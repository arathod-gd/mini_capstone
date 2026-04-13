package org.paybridge.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ParseLogRepository {

    public void initialize() throws SQLException {
        String sql = """
                CREATE TABLE IF NOT EXISTS iso_parse_logs (
                    id BIGSERIAL PRIMARY KEY,
                    raw_message TEXT NOT NULL,
                    status VARCHAR(20) NOT NULL,
                    payload JSONB NOT NULL,
                    error_code VARCHAR(100),
                    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                )
                """;

        try (Connection connection = DatabaseConnectionFactory.openConnection();
             Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    public void save(String rawMessage, String status, String payload, String errorCode) throws SQLException {
        String sql = """
                INSERT INTO iso_parse_logs (raw_message, status, payload, error_code)
                VALUES (?, ?, CAST(? AS JSONB), ?)
                """;

        try (Connection connection = DatabaseConnectionFactory.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, rawMessage);
            statement.setString(2, status);
            statement.setString(3, payload);
            statement.setString(4, errorCode);
            statement.executeUpdate();
        }
    }

    public List<ParseLogRecord> findAll() throws SQLException {
        String sql = """
                SELECT id, raw_message, status, payload::text AS payload, error_code, created_at
                FROM iso_parse_logs
                ORDER BY created_at ASC, id ASC
                """;

        List<ParseLogRecord> records = new ArrayList<>();

        try (Connection connection = DatabaseConnectionFactory.openConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                records.add(new ParseLogRecord(
                        resultSet.getLong("id"),
                        resultSet.getString("raw_message"),
                        resultSet.getString("status"),
                        resultSet.getString("payload"),
                        resultSet.getString("error_code"),
                        resultSet.getTimestamp("created_at").toLocalDateTime()
                ));
            }
        }

        return records;
    }
}
