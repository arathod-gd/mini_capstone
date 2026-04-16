# PayBridge ISO 8583 Parser

This project is a Java-based ISO 8583 parser for primary-bitmap messages. It reads an ISO 8583 message string, parses the fields present in the primary bitmap, maps the parsed values into a response DTO, and prints the result as JSON.

## Scope

- Supports primary bitmap parsing only
- Parses ISO 8583 fields from `2` to `64`
- Supports fixed-length, `LLVAR`, and `LLLVAR` fields
- Returns structured JSON for both success and parser errors

## Current Limitation

- Secondary bitmap is not handled yet
- Fields above `64` are not parsed

## Project Structure

- `src/main/java/org/paybridge/parser/ISOParser.java`
  Main parsing logic
- `src/main/java/org/paybridge/model/ISOMessage.java`
  ISO 8583 field model with annotations
- `src/main/java/org/paybridge/mapper/ISOMapper.java`
  Maps parsed ISO data into response DTO
- `src/main/java/org/paybridge/dto/ISOResponse.java`
  Output DTO converted to JSON
- `src/main/java/org/paybridge/dto/ErrorResponse.java`
  Error DTO for parser failures
- `src/test/java/org/paybridge/parser/ISOParserAllFieldsTest.java`
  Test coverage for all annotated primary-bitmap fields

## Requirements

- Java 17 or higher
- Maven 3.9+ recommended
- Docker Desktop or Docker Engine for PostgreSQL

## PostgreSQL Setup

The project now connects to PostgreSQL using the values in `.env`.

Start the database:

```bash
docker compose up -d database
```

Default connection settings:

- `POSTGRES_HOST=localhost`
- `POSTGRES_PORT=5432`
- `POSTGRES_DB=postgres`
- `POSTGRES_USER=postgres`
- `POSTGRES_PASSWORD=postgres`
- `APP_DB_NAME=mini_capstone_db`

Docker mounts [docker/init.sql](/Users/arathod/Downloads/mini-capstone-project/docker/init.sql), which:

- creates the `mini_capstone_db` database if it does not already exist
- creates the `iso_parse_logs` table if it does not already exist

The PostgreSQL data directory is persisted in the named Docker volume `mini_capstone_data`.

When the CLI starts, it connects to `APP_DB_NAME` and stores every parse attempt with:

- the raw ISO message
- success or error status
- the JSON payload returned by the parser
- the parser error code when applicable

The repository also contains database-assignment demos and scripts:

- [docs/database-assignment-report.md](/Users/arathod/Downloads/mini-capstone-project/docs/database-assignment-report.md)
- `org.paybridge.db.demo.TransactionConsistencyDemo`
- `org.paybridge.db.demo.IsolationLevelDemo`
- [scripts/sql/02-populate-settlement-events.sql](/Users/arathod/Downloads/mini-capstone-project/scripts/sql/02-populate-settlement-events.sql)
- [scripts/sql/03-index-experiments.sql](/Users/arathod/Downloads/mini-capstone-project/scripts/sql/03-index-experiments.sql)

## Database Assignment Verification

Use this checklist to confirm the task is fully completed.

### 1. Start PostgreSQL

```bash
docker compose up -d database
```

Expected:

- the container starts successfully
- PostgreSQL listens on `localhost:5432`

### 2. Check Consistency Demo

```bash
mvn -q exec:java -Dexec.mainClass=org.paybridge.db.demo.TransactionConsistencyDemo
```

Task is correct if:

- the first scenario shows an inconsistent result after failure without a transaction
- total balance drops from `2000.00` to `1700.00`
- the second scenario rolls back and keeps total balance at `2000.00`

### 3. Check Isolation Level Demo

```bash
mvn -q exec:java -Dexec.mainClass=org.paybridge.db.demo.IsolationLevelDemo
```

Task is correct if:

- under `READ COMMITTED`, the second count is larger than the first count
- under `REPEATABLE READ`, both counts stay the same

Typical expected output:

```text
Default isolation (READ COMMITTED)
  First count  = 3
  Second count = 4

Correct isolation for a stable settlement snapshot (REPEATABLE READ)
  First count  = 3
  Second count = 3
```

### 4. Load Large Dataset

```bash
psql -U postgres -d mini_capstone_db -f scripts/sql/02-populate-settlement-events.sql
```

Task is correct if:

- the script finishes successfully
- `settlement_events` contains `2000000` rows

Optional check:

```bash
psql -U postgres -d mini_capstone_db -c "SELECT COUNT(*) FROM settlement_events;"
```

### 5. Check Index Experiments

```bash
psql -U postgres -d mini_capstone_db -f scripts/sql/03-index-experiments.sql
```

Task is correct if:

- before creating `idx_settlement_terminal_id`, PostgreSQL uses a sequential scan for the terminal lookup
- after creating `idx_settlement_terminal_id`, PostgreSQL uses an index-based plan
- the compound index `(merchant_id, business_date, response_code)` works best when the query uses all columns
- the same compound index is still useful for left-prefix queries
- queries that skip the leading column are not helped much by that compound index

### 6. Final Check

The task is complete when all of these are true:

- both JDBC demos run successfully
- the observed outputs match [docs/database-assignment-report.md](/Users/arathod/Downloads/mini-capstone-project/docs/database-assignment-report.md)
- the bulk-load script inserts 2 million rows
- the execution plans confirm the expected index behavior

## Run The Project

Start the CLI parser:

```powershell
mvn exec:java -Dexec.mainClass=org.paybridge.Main
```

Then enter an ISO 8583 message when prompted.

## Run Tests

```powershell
mvn test
```

This runs the parser tests, including the test that checks every annotated field from `2` to `64`.

## How Parsing Works

Each incoming message is read in this order:

1. `MTI` - first 4 characters
2. Primary bitmap - next 16 hex characters
3. Field values - parsed in ascending field-number order based on bitmap bits

Important rule:

- The message body must contain only the values for fields marked as present in the bitmap
- Those values must appear in ascending field order

## Input Examples

### Example 1: Fixed-Length Fields

Input:

```text
02003020000000000000000000000000001000123456
```

Meaning:

- `0200` = MTI
- `3020000000000000` = primary bitmap
- Fields present:
  - `3` processing code
  - `4` amount transaction
  - `11` STAN

Expected parsed values:

- `processingCode = 000000`
- `amountTransaction = 000000001000`
- `stan = 123456`

### Example 2: LLVAR + Fixed-Length Fields

Input:

```text
02007020000000800000161234567890123456000000000000001000123456TERMID01
```

Meaning:

- `0200` = MTI
- `7020000000800000` = primary bitmap
- Fields present:
  - `2` PAN
  - `3` processing code
  - `4` amount transaction
  - `11` STAN
  - `41` terminal ID

Expected parsed values:

- `pan = 1234567890123456`
- `processingCode = 000000`
- `amountTransaction = 000000001000`
- `stan = 123456`
- `terminalId = TERMID01`

### Example 3: Field 64 Only

Input:

```text
02000000000000000001ABCDEF1234567890
```

Meaning:

- `0200` = MTI
- `0000000000000001` = only field `64` is present
- `ABCDEF1234567890` = MAC value

Expected parsed value:

- `mac = ABCDEF1234567890`

## Error Examples

### Invalid Bitmap

Input:

```text
0200Z000000000000000
```

Expected result:

- Parser returns JSON error response
- Does not crash with raw `NumberFormatException`

### Invalid LLVAR Header

Input:

```text
02004000000000000000AB1234
```

Expected result:

- Parser returns JSON error response for invalid length header

### Oversized LLVAR Value

Input:

```text
020040000000000000002012345678901234567890
```

Expected result:

- Parser returns JSON error response because field `2` exceeds its allowed length

## Notes About Bitmaps

- Field presence is decided only by the primary bitmap
- Bit `1` indicates secondary bitmap in full ISO 8583 implementations
- This project currently leaves that case unsupported by design

## Testing Strategy

The test suite includes:

- one test per annotated ISO field from `2` to `64`
- fixed-length field checks
- `LLVAR` field checks
- `LLLVAR` field checks

Run:

```powershell
mvn test
```

## Sample Output

For a valid message, output is JSON similar to:

```json
{
  "mti": "0200",
  "pan": "1234567890123456",
  "processingCode": "000000",
  "amountTransaction": "000000001000",
  "stan": "123456",
  "terminalId": "TERMID01"
}
```

For an invalid message, output is JSON similar to:

```json
{
  "errorCode": "INVALID_BITMAP",
  "message": "Bitmap contains non-hex characters",
  "fieldNumber": 1
}
```

## Summary

This project is a primary-bitmap ISO 8583 parser intended for learning, testing, and demonstrating ISO field parsing using Java, annotations, reflection, and JSON conversion.
