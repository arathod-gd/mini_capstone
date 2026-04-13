package org.paybridge.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DatabaseConnectionFactory {

    private DatabaseConnectionFactory() {
    }

    public static Connection openConnection() throws SQLException {
        DatabaseConfig config = DatabaseConfig.load();
        return DriverManager.getConnection(
                config.jdbcUrl(),
                config.username(),
                config.password()
        );
    }
}
