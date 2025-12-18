const sqlite3 = require("sqlite3").verbose();
const fs = require("fs");

const DB_FILE = "./app.db";
const db = new sqlite3.Database(DB_FILE);

function init() {
  const schema = fs.readFileSync("./schema.sql", "utf-8");
  db.exec(schema);
}

module.exports = { db, init };
