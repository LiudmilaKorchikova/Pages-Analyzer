DROP TABLE IF EXISTS url_checks;
DROP TABLE IF EXISTS urls;

CREATE TABLE urls (
    id SERIAL PRIMARY KEY,
    name TEXT NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE url_checks (
    id SERIAL PRIMARY KEY,
    url_id BIGINT NOT NULL,
    status_code INT,
    title TEXT,
    h1 TEXT,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    FOREIGN KEY (url_id) REFERENCES urls(id) ON DELETE CASCADE
);