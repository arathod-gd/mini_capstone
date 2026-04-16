package org.paybridge.db.demo;

import org.paybridge.db.DatabaseConnectionFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

public final class DemoSchemaManager {

    private DemoSchemaManager() {
    }

    public static void initializeSchema() throws SQLException {
        try (Connection connection = DatabaseConnectionFactory.openConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS payment_accounts (
                        id BIGSERIAL PRIMARY KEY,
                        account_number VARCHAR(30) NOT NULL UNIQUE,
                        holder_name VARCHAR(100) NOT NULL,
                        balance NUMERIC(15, 2) NOT NULL,
                        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                    )
                    """);

            statement.execute("""
                    CREATE TABLE IF NOT EXISTS transfer_audit (
                        id BIGSERIAL PRIMARY KEY,
                        from_account_id BIGINT NOT NULL REFERENCES payment_accounts (id),
                        to_account_id BIGINT NOT NULL REFERENCES payment_accounts (id),
                        amount NUMERIC(15, 2) NOT NULL,
                        status VARCHAR(30) NOT NULL,
                        note TEXT,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                    )
                    """);

            statement.execute("""
                    CREATE TABLE IF NOT EXISTS settlement_events (
                        id BIGSERIAL PRIMARY KEY,
                        merchant_id VARCHAR(30) NOT NULL,
                        terminal_id VARCHAR(20) NOT NULL,
                        response_code VARCHAR(2) NOT NULL,
                        amount NUMERIC(15, 2) NOT NULL,
                        business_date DATE NOT NULL,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                    )
                    """);
        }
    }

    public static void resetConsistencyDemoData() throws SQLException {
        try (Connection connection = DatabaseConnectionFactory.openConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE transfer_audit RESTART IDENTITY");
            statement.execute("TRUNCATE TABLE payment_accounts RESTART IDENTITY CASCADE");
        }

        insertAccount("ACC-001", "Issuer settlement account", new BigDecimal("1000.00"));
        insertAccount("ACC-002", "Merchant payout account", new BigDecimal("1000.00"));
    }

    public static void resetIsolationDemoData() throws SQLException {
        try (Connection connection = DatabaseConnectionFactory.openConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM settlement_events WHERE merchant_id = 'M-DEMO-001'");
        }

        insertSettlementEvent("M-DEMO-001", "TERM1001", "00", new BigDecimal("50.00"), LocalDate.of(2026, 4, 15));
        insertSettlementEvent("M-DEMO-001", "TERM1001", "00", new BigDecimal("75.00"), LocalDate.of(2026, 4, 15));
        insertSettlementEvent("M-DEMO-001", "TERM1001", "00", new BigDecimal("90.00"), LocalDate.of(2026, 4, 15));
    }

    public static void insertSettlementEvent(String merchantId,
                                             String terminalId,
                                             String responseCode,
                                             BigDecimal amount,
                                             LocalDate businessDate) throws SQLException {
        try (Connection connection = DatabaseConnectionFactory.openConnection();
             PreparedStatement statement = connection.prepareStatement("""
                     INSERT INTO settlement_events
                     (merchant_id, terminal_id, response_code, amount, business_date)
                     VALUES (?, ?, ?, ?, ?)
                     """)) {
            statement.setString(1, merchantId);
            statement.setString(2, terminalId);
            statement.setString(3, responseCode);
            statement.setBigDecimal(4, amount);
            statement.setDate(5, Date.valueOf(businessDate));
            statement.executeUpdate();
        }
    }

    private static void insertAccount(String accountNumber,
                                      String holderName,
                                      BigDecimal balance) throws SQLException {
        try (Connection connection = DatabaseConnectionFactory.openConnection();
             PreparedStatement statement = connection.prepareStatement("""
                     INSERT INTO payment_accounts (account_number, holder_name, balance)
                     VALUES (?, ?, ?)
                     """)) {
            statement.setString(1, accountNumber);
            statement.setString(2, holderName);
            statement.setBigDecimal(3, balance);
            statement.executeUpdate();
        }
    }
}
