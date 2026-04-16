\connect mini_capstone_db

TRUNCATE TABLE settlement_events RESTART IDENTITY;

INSERT INTO settlement_events (
    merchant_id,
    terminal_id,
    response_code,
    amount,
    business_date,
    created_at
)
SELECT
    'M' || LPAD((series % 5000)::text, 5, '0'),
    'T' || LPAD((series % 2000)::text, 6, '0'),
    CASE
        WHEN series % 10 < 8 THEN '00'
        WHEN series % 10 = 8 THEN '05'
        ELSE '91'
    END,
    ((series % 500000) + 100)::numeric / 100,
    DATE '2026-01-01' + (series % 120),
    TIMESTAMP '2026-01-01 00:00:00' + (series || ' seconds')::interval
FROM generate_series(1, 2000000) AS series;

ANALYZE settlement_events;
