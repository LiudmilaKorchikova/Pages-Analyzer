DROP TABLE IF EXISTS urls;

CREATE TABLE urls (
                      id INTEGER PRIMARY KEY AUTOINCREMENT,
                      name TEXT NOT NULL UNIQUE,
                      created_at TEXT NOT NULL
);