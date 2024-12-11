DROP TABLE IF EXISTS urls;

CREATE TABLE urls (
                      id SERIAL PRIMARY KEY,
                      name TEXT NOT NULL UNIQUE,
                      created_at TEXT NOT NULL
);