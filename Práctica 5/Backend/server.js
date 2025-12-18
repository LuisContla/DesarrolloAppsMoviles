require("dotenv").config();
const express = require("express");
const cors = require("cors");
const bcrypt = require("bcrypt");
const jwt = require("jsonwebtoken");
const { db, init } = require("./db");

init();

const app = express();
app.use(cors());
app.use(express.json());

const JWT_SECRET = process.env.JWT_SECRET || "dev_secret";

db.get("SELECT COUNT(*) as c FROM items", [], (err, row) => {
  if (row && row.c === 0) {
    const now = Date.now();
    const stmt = db.prepare("INSERT INTO items(title,description,category,rating,updated_at,created_at) VALUES(?,?,?,?,?,?)");
    [
      ["Breaking Bad", "Serie recomendada", "Series", 4.9],
      ["Interstellar", "Pelicula sci-fi", "Peliculas", 4.7],
      ["Kotlin Tips", "Notas de Android", "Notas", 4.5],
      ["Stranger Things", "Serie misteriosa", "Series", 4.6],
    ].forEach(x => stmt.run([x[0], x[1], x[2], x[3], now, now]));
    stmt.finalize();
  }
});

function signToken(user) {
  return jwt.sign(
    { sub: user.id, role: user.role, name: user.name, email: user.email },
    JWT_SECRET,
    { expiresIn: "7d" }
  );
}

function auth(req, res, next) {
  const h = req.headers.authorization || "";
  const token = h.startsWith("Bearer ") ? h.slice(7) : null;
  if (!token) return res.status(401).json({ message: "No token" });
  try {
    req.user = jwt.verify(token, JWT_SECRET);
    next();
  } catch {
    return res.status(401).json({ message: "Invalid token" });
  }
}

function adminOnly(req, res, next) {
  if (req.user?.role !== "ADMIN") return res.status(403).json({ message: "Forbidden" });
  next();
}

/** AUTH */

app.get("/", (req, res) => {
  res.send("Backend OK ✅");
});


app.post("/auth/register", async (req, res) => {
  const { name, email, password, role } = req.body;
  if (!name || !email || !password) return res.status(400).json({ message: "Missing fields" });

  const hash = await bcrypt.hash(password, 10);
  const createdAt = Date.now();
  const userRole = role === "ADMIN" ? "ADMIN" : "USER";

  db.run(
    "INSERT INTO users(name,email,password_hash,role,created_at) VALUES(?,?,?,?,?)",
    [name, email, hash, userRole, createdAt],
    function (err) {
      if (err) return res.status(409).json({ message: "Email already exists" });
      const user = { id: this.lastID, name, email, role: userRole };
      res.json({ token: signToken(user), user });
    }
  );
});

app.post("/auth/login", (req, res) => {
  const { email, password } = req.body;
  db.get("SELECT * FROM users WHERE email = ?", [email], async (err, row) => {
    if (err || !row) return res.status(401).json({ message: "Invalid credentials" });
    const ok = await bcrypt.compare(password, row.password_hash);
    if (!ok) return res.status(401).json({ message: "Invalid credentials" });
    const user = { id: row.id, name: row.name, email: row.email, role: row.role };
    res.json({ token: signToken(user), user });
  });
});

app.get("/me", auth, (req, res) => res.json({ user: req.user }));

app.get("/items", auth, (req, res) => {
  const q = (req.query.q || "").toString().trim();
  const category = (req.query.category || "").toString().trim();
  const minRating = req.query.minRating ? Number(req.query.minRating) : null;
  const sort = (req.query.sort || "updated_desc").toString(); // updated_desc, rating_desc, title_asc
  const limit = req.query.limit ? Math.min(Number(req.query.limit), 100) : 50;
  const offset = req.query.offset ? Number(req.query.offset) : 0;

  const where = [];
  const args = [];

  if (q) {
  where.push("(title LIKE ? OR description LIKE ?)");
  args.push(`%${q}%`, `%${q}%`);
  }
  if (category) {
    where.push("category = ?");
    args.push(category);
  }
  if (minRating !== null && !Number.isNaN(minRating)) {
    where.push("rating >= ?");
    args.push(minRating);
  }

  let orderBy = "updated_at DESC";
  if (sort === "rating_desc") orderBy = "rating DESC";
  if (sort === "title_asc") orderBy = "title ASC";

  const whereSql = where.length ? `WHERE ${where.join(" AND ")}` : "";
  const sql = `SELECT * FROM items ${whereSql} ORDER BY ${orderBy} LIMIT ? OFFSET ?`;
  args.push(limit, offset);

  db.all(sql, args, (err, rows) => {
  if (err) {
    console.error("ITEMS SQL ERROR:", err.message);
    console.error("SQL:", sql);
    console.error("ARGS:", args);
    return res.status(500).json({ message: "db error", detail: err.message });
  }
  res.json(rows || []);
});

});

app.get("/items/:id", auth, (req, res) => {
  db.get("SELECT * FROM items WHERE id = ?", [req.params.id], (err, row) => {
    if (err || !row) return res.status(404).json({ message: "Not found" });
    res.json(row);
  });
});


/** USER DATA */
app.get("/history", auth, (req, res) => {
  db.all(
    "SELECT * FROM search_history WHERE user_id=? ORDER BY created_at DESC LIMIT 100",
    [req.user.sub],
    (err, rows) => res.json(rows || [])
  );
});

app.post("/history", auth, (req, res) => {
  const { query, source } = req.body;
  if (!query || !source) return res.status(400).json({ message: "Missing" });
  db.run(
    "INSERT INTO search_history(user_id,query,source,created_at) VALUES(?,?,?,?)",
    [req.user.sub, query, source, Date.now()],
    function () {
      res.json({ id: this.lastID });
    }
  );
});

app.get("/favorites", auth, (req, res) => {
  db.all(
    "SELECT * FROM favorites WHERE user_id=? ORDER BY created_at DESC",
    [req.user.sub],
    (err, rows) => res.json(rows || [])
  );
});

app.post("/favorites", auth, (req, res) => {
  const { source, itemId, title, imageUrl, genres, summary } = req.body;
  if (!source || !itemId || !title) return res.status(400).json({ message: "Missing" });

  db.run(
    `INSERT OR IGNORE INTO favorites(user_id,source,item_id,title,image_url,genres,summary,created_at)
     VALUES(?,?,?,?,?,?,?,?)`,
    [req.user.sub, source, String(itemId), title, imageUrl || null, genres || null, summary || null, Date.now()],
    function () {
      res.json({ ok: true });
    }
  );
});

app.delete("/favorites/:source/:itemId", auth, (req, res) => {
  db.run(
    "DELETE FROM favorites WHERE user_id=? AND source=? AND item_id=?",
    [req.user.sub, req.params.source, String(req.params.itemId)],
    () => res.json({ ok: true })
  );
});

/** ADMIN */
app.get("/admin/users", auth, adminOnly, (req, res) => {
  db.all("SELECT id,name,email,role,created_at FROM users ORDER BY created_at DESC", [], (err, rows) =>
    res.json(rows || [])
  );
});
app.get("/admin/history/:userId", auth, adminOnly, (req, res) => {
  db.all("SELECT * FROM search_history WHERE user_id=? ORDER BY created_at DESC", [req.params.userId], (e, rows) =>
    res.json(rows || [])
  );
});
app.get("/admin/favorites/:userId", auth, adminOnly, (req, res) => {
  db.all("SELECT * FROM favorites WHERE user_id=? ORDER BY created_at DESC", [req.params.userId], (e, rows) =>
    res.json(rows || [])
  );
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, "0.0.0.0", () => console.log("Backend running on", PORT));