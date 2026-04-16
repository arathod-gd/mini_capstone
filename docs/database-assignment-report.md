# Database Assignment Report

## Completion Status

- Done: JDBC demo that shows how the database can be pushed into an inconsistent state when a money transfer is executed without a transaction.
- Done: JDBC demo that repeats the same transfer with an explicit transaction and rollback.
- Done: JDBC demo that shows why a non-default isolation level is needed for settlement reporting.
- Done: SQL script that populates `settlement_events` with 2,000,000 rows.
- Done: SQL script that compares query plans and execution times with and without indexes.
- Done: Compound index use case with observations for full-column and partial-column predicates.
- Pending local verification: collect the exact `EXPLAIN ANALYZE` timings on your PostgreSQL instance after loading the data.

## Domain And Relational Model

This project already stores ISO 8583 parse attempts in `iso_parse_logs`. To demonstrate ACID, isolation levels, and indexing in a payment-processing domain, three additional tables were introduced:

- `payment_accounts`: account balances for issuer and merchant settlement accounts.
- `transfer_audit`: records transfer attempts between accounts.
- `settlement_events`: merchant transaction events used for settlement and reporting queries.

These tables fit the existing payment-message domain better than an abstract sample model because they represent the downstream persistence that would happen after ISO 8583 messages are parsed.

## 1. Consistency (C in ACID)

### Case

A transfer moves `300.00` from the issuer settlement account to the merchant payout account. The invariant is that the combined balance of both accounts must stay `2000.00`.

If the debit succeeds, but the application crashes before the credit is applied, the database reaches an inconsistent state:

- sender balance decreases
- receiver balance does not increase
- total money in the system changes

### Demo Code

- [TransactionConsistencyDemo.java](/Users/arathod/Downloads/mini-capstone-project/src/main/java/org/paybridge/db/demo/TransactionConsistencyDemo.java)
- [DemoSchemaManager.java](/Users/arathod/Downloads/mini-capstone-project/src/main/java/org/paybridge/db/demo/DemoSchemaManager.java)

### Run

```bash
mvn exec:java -Dexec.mainClass=org.paybridge.db.demo.TransactionConsistencyDemo
```

### Expected Behavior

Without a transaction:

- initial balances: `1000.00` and `1000.00`
- debit succeeds
- simulated crash happens
- final balances become `700.00` and `1000.00`
- invariant is broken because total balance becomes `1700.00`

With a transaction:

- debit happens inside one database transaction
- simulated crash triggers `rollback()`
- final balances stay `1000.00` and `1000.00`
- invariant is preserved

## 2. Non-Default Isolation Level

### Case

Settlement reporting needs a stable snapshot while a concurrent payment is being inserted. PostgreSQL defaults to `READ COMMITTED`, which allows a transaction to see newly committed rows on a later `SELECT`. For financial reporting, that can produce inconsistent counts and totals inside one logical report.

The correct level here is `REPEATABLE READ`, because the report should see one consistent snapshot from start to finish.

### Demo Code

- [IsolationLevelDemo.java](/Users/arathod/Downloads/mini-capstone-project/src/main/java/org/paybridge/db/demo/IsolationLevelDemo.java)

### Run

```bash
mvn exec:java -Dexec.mainClass=org.paybridge.db.demo.IsolationLevelDemo
```

### Expected Behavior

Using default `READ COMMITTED`:

- transaction A counts approved events for merchant `M-DEMO-001`
- transaction B inserts one more approved event and commits
- transaction A runs the same count again
- the second count is larger than the first one

Typical output:

```text
Default isolation (READ COMMITTED)
  First count  = 3
  Second count = 4
```

Using `REPEATABLE READ`:

- transaction A keeps the same snapshot for both reads
- transaction B still commits successfully
- transaction A does not see the newly inserted row until after it commits

Typical output:

```text
Correct isolation for a stable settlement snapshot (REPEATABLE READ)
  First count  = 3
  Second count = 3
```

## 3. Indexing With Millions Of Rows

### Population Script

- [02-populate-settlement-events.sql](/Users/arathod/Downloads/mini-capstone-project/scripts/sql/02-populate-settlement-events.sql)

This script loads `2,000,000` rows into `settlement_events` with realistic distributions for merchant, terminal, response code, amount, and business date.

### Run

```bash
psql -U postgres -d mini_capstone_db -f scripts/sql/02-populate-settlement-events.sql
```

### Single-Column Index Case

Query:

```sql
SELECT *
FROM settlement_events
WHERE terminal_id = 'T000123';
```

Without `idx_settlement_terminal_id`, PostgreSQL is expected to use a sequential scan across the whole table.

With `idx_settlement_terminal_id`, PostgreSQL is expected to switch to an index scan or bitmap index scan, reducing execution time significantly because only matching terminal rows need to be visited.

## 4. Compound Index

### Use Case

Settlement back-office screens often filter by:

- merchant
- business date
- response code

That makes `(merchant_id, business_date, response_code)` a suitable compound index.

### Experiment Script

- [03-index-experiments.sql](/Users/arathod/Downloads/mini-capstone-project/scripts/sql/03-index-experiments.sql)

### Queries And Observations

Query using the whole index key:

```sql
SELECT *
FROM settlement_events
WHERE merchant_id = 'M01234'
  AND business_date = DATE '2026-02-10'
  AND response_code = '00';
```

Observation:

- best fit for the compound index
- PostgreSQL can use the full key order efficiently

Query using the leftmost prefix:

```sql
SELECT *
FROM settlement_events
WHERE merchant_id = 'M01234'
  AND business_date = DATE '2026-02-10';
```

Observation:

- still uses the compound index efficiently
- left-prefix columns remain searchable

Query using only the first column:

```sql
SELECT *
FROM settlement_events
WHERE merchant_id = 'M01234';
```

Observation:

- index is still useful because the predicate starts with the leading column

Query skipping the leading column:

```sql
SELECT *
FROM settlement_events
WHERE business_date = DATE '2026-02-10'
  AND response_code = '00';
```

Observation:

- this usually cannot use the compound index efficiently as the main access path
- because the leading column `merchant_id` is missing, PostgreSQL often falls back to a sequential scan or a less selective plan

## Suggested Mentor Discussion Points

- Why `READ COMMITTED` is acceptable for OLTP updates but weak for reporting snapshots.
- Why `REPEATABLE READ` is enough for the report scenario, while `SERIALIZABLE` would be heavier.
- Why compound indexes depend on left-prefix ordering.
- Why indexes accelerate reads but make inserts and updates more expensive.
