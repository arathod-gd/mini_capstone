package org.paybridge.db;

public record DatabaseConfig(
        String host,
        int port,
        String database,
        String username,
        String password
) {

    public static DatabaseConfig load() {
        String host = EnvFileLoader.get("POSTGRES_HOST", "localhost");
        int port = Integer.parseInt(EnvFileLoader.get("POSTGRES_PORT", "5432"));
        String database = EnvFileLoader.get("APP_DB_NAME", EnvFileLoader.get("POSTGRES_DB", "mini_capstone_db"));
        String username = EnvFileLoader.get("POSTGRES_USER", "postgres");
        String password = EnvFileLoader.get("POSTGRES_PASSWORD", "postgres");

        return new DatabaseConfig(host, port, database, username, password);
    }

    public String jdbcUrl() {
        return "jdbc:postgresql://" + host + ":" + port + "/" + database;
    }
}
