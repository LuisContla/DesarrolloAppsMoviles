PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS users (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  name TEXT NOT NULL,
  email TEXT NOT NULL UNIQUE,
  password_hash TEXT NOT NULL,
  role TEXT NOT NULL DEFAULT 'USER', -- USER | ADMIN
  created_at INTEGER NOT NULL
);

CREATE TABLE IF NOT EXISTS search_history (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  user_id INTEGER NOT NULL,
  query TEXT NOT NULL,
  source TEXT NOT NULL, -- TVMAZE | MI_API
  created_at INTEGER NOT NULL,
  synced_at INTEGER,
  FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS favorites (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  user_id INTEGER NOT NULL,
  source TEXT NOT NULL, -- TVMAZE
  item_id TEXT NOT NULL, -- id externo (TVMaze show id)
  title TEXT NOT NULL,
  image_url TEXT,
  genres TEXT, -- CSV
  summary TEXT,
  created_at INTEGER NOT NULL,
  synced_at INTEGER,
  UNIQUE(user_id, source, item_id),
  FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS items (
  id INTEGER PRIMARY KEY AUTOINCREMENT,
  title TEXT NOT NULL,
  description TEXT,
  category TEXT NOT NULL,
  rating REAL NOT NULL DEFAULT 0,
  updated_at INTEGER NOT NULL,
  created_at INTEGER NOT NULL
);
