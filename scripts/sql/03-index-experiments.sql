\connect mini_capstone_db

DROP INDEX IF EXISTS idx_settlement_terminal_id;
DROP INDEX IF EXISTS idx_settlement_merchant_date_response;

EXPLAIN (ANALYZE, BUFFERS)
SELECT *
FROM settlement_events
WHERE terminal_id = 'T000123';

CREATE INDEX idx_settlement_terminal_id
    ON settlement_events (terminal_id);

ANALYZE settlement_events;

EXPLAIN (ANALYZE, BUFFERS)
SELECT *
FROM settlement_events
WHERE terminal_id = 'T000123';

CREATE INDEX idx_settlement_merchant_date_response
    ON settlement_events (merchant_id, business_date, response_code);

ANALYZE settlement_events;

EXPLAIN (ANALYZE, BUFFERS)
SELECT *
FROM settlement_events
WHERE merchant_id = 'M01234'
  AND business_date = DATE '2026-02-10'
  AND response_code = '00';

EXPLAIN (ANALYZE, BUFFERS)
SELECT *
FROM settlement_events
WHERE merchant_id = 'M01234'
  AND business_date = DATE '2026-02-10';

EXPLAIN (ANALYZE, BUFFERS)
SELECT *
FROM settlement_events
WHERE merchant_id = 'M01234';

EXPLAIN (ANALYZE, BUFFERS)
SELECT *
FROM settlement_events
WHERE business_date = DATE '2026-02-10'
  AND response_code = '00';
