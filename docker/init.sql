\connect postgres

SELECT 'CREATE DATABASE mini_capstone_db'
WHERE NOT EXISTS (
    SELECT FROM pg_database WHERE datname = 'mini_capstone_db'
)\gexec

\connect mini_capstone_db

CREATE TABLE IF NOT EXISTS iso_parse_logs (
    id BIGSERIAL PRIMARY KEY,
    raw_message TEXT NOT NULL,
    status VARCHAR(20) NOT NULL,
    payload JSONB NOT NULL,
    error_code VARCHAR(100),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
