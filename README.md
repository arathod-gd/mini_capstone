PayBridge is a Java mini-capstone project for translating legacy bank data into JSON-friendly objects.

Current demo flow:
- Parse a fixed-width bank record with reflection.
- Use Java generics to map the record into a typed POJO.
- Create the PostgreSQL table from `init.sql`.
- Store the parsed transaction in PostgreSQL with plain JDBC.
- Read the stored transaction back and serialize it as JSON.

Sample input record:
`BANK00011234567890123456000000000000012520250407REF000123456`

Field layout:
- 1-8: bank code
- 9-24: account number
- 25-30: transaction code
- 31-40: amount
- 41-48: settlement date (`yyyyMMdd`)
- 49-60: reference number

Run:
`mvn test`

Start PostgreSQL in Docker:
`docker compose up -d`

The table is created automatically from [`init.sql`](/Users/arathod/Desktop/MiniCapstone/mini-capstone-project/init.sql) when the container initializes.

`mvn exec:java -Dexec.mainClass=org.paybridge.Main`

Database connection defaults:
- Host: `localhost`
- Port: `55432`
- Database: `paybridge`
- Username: `paybridge_user`
- Password: `paybridge_password`

You can override them with environment variables:
- `DB_URL`
- `DB_USERNAME`
- `DB_PASSWORD`
