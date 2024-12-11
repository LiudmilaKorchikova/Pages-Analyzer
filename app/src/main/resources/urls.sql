DROP TABLE IF EXISTS urls;

CREATE TABLE urls (
                      id INTEGER PRIMARY KEY AUTO_INCREMENT,
                      name TEXT NOT NULL UNIQUE,
                      created_at TEXT NOT NULL
);