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

CREATE TABLE IF NOT EXISTS payment_accounts (
    id BIGSERIAL PRIMARY KEY,
    account_number VARCHAR(30) NOT NULL UNIQUE,
    holder_name VARCHAR(100) NOT NULL,
    balance NUMERIC(15, 2) NOT NULL,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS transfer_audit (
    id BIGSERIAL PRIMARY KEY,
    from_account_id BIGINT NOT NULL REFERENCES payment_accounts (id),
    to_account_id BIGINT NOT NULL REFERENCES payment_accounts (id),
    amount NUMERIC(15, 2) NOT NULL,
    status VARCHAR(30) NOT NULL,
    note TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS settlement_events (
    id BIGSERIAL PRIMARY KEY,
    merchant_id VARCHAR(30) NOT NULL,
    terminal_id VARCHAR(20) NOT NULL,
    response_code VARCHAR(2) NOT NULL,
    amount NUMERIC(15, 2) NOT NULL,
    business_date DATE NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
