package org.paybridge.db.demo;

import org.paybridge.db.DatabaseConnectionFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class TransactionConsistencyDemo {

    private static final BigDecimal TRANSFER_AMOUNT = new BigDecimal("300.00");

    private TransactionConsistencyDemo() {
    }

    public static void main(String[] args) throws Exception {
        DemoSchemaManager.initializeSchema();

        System.out.println("=== Consistency demo without an explicit transaction ===");
        DemoSchemaManager.resetConsistencyDemoData();
        printBalances("Before transfer");
        try {
            transferWithoutTransaction(1L, 2L, TRANSFER_AMOUNT);
        } catch (SQLException ex) {
            System.out.println("Failure after debit: " + ex.getMessage());
        }
        printBalances("After transfer without transaction");

        System.out.println();
        System.out.println("=== Consistency demo with an explicit transaction ===");
        DemoSchemaManager.resetConsistencyDemoData();
        printBalances("Before transfer");
        try {
            transferWithTransaction(1L, 2L, TRANSFER_AMOUNT);
        } catch (SQLException ex) {
            System.out.println("Failure after debit: " + ex.getMessage());
        }
        printBalances("After transfer with transaction");
    }

    private static void transferWithoutTransaction(long fromAccountId,
                                                   long toAccountId,
                                                   BigDecimal amount) throws SQLException {
        try (Connection connection = DatabaseConnectionFactory.openConnection()) {
            debit(connection, fromAccountId, amount);
            insertAuditRow(connection, fromAccountId, toAccountId, amount, "FAILED", "Crash after debit before credit");
            throw new SQLException("Simulated application crash");
        }
    }

    private static void transferWithTransaction(long fromAccountId,
                                                long toAccountId,
                                                BigDecimal amount) throws SQLException {
        try (Connection connection = DatabaseConnectionFactory.openConnection()) {
            connection.setAutoCommit(false);
            try {
                debit(connection, fromAccountId, amount);
                insertAuditRow(connection, fromAccountId, toAccountId, amount, "FAILED", "Crash after debit before credit");
                throw new SQLException("Simulated application crash");
            } catch (SQLException ex) {
                connection.rollback();
                throw ex;
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }

    private static void debit(Connection connection, long accountId, BigDecimal amount) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
                UPDATE payment_accounts
                SET balance = balance - ?, updated_at = CURRENT_TIMESTAMP
                WHERE id = ?
                """)) {
            statement.setBigDecimal(1, amount);
            statement.setLong(2, accountId);
            statement.executeUpdate();
        }
    }

    private static void insertAuditRow(Connection connection,
                                       long fromAccountId,
                                       long toAccountId,
                                       BigDecimal amount,
                                       String status,
                                       String note) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
                INSERT INTO transfer_audit (from_account_id, to_account_id, amount, status, note)
                VALUES (?, ?, ?, ?, ?)
                """)) {
            statement.setLong(1, fromAccountId);
            statement.setLong(2, toAccountId);
            statement.setBigDecimal(3, amount);
            statement.setString(4, status);
            statement.setString(5, note);
            statement.executeUpdate();
        }
    }

    private static void printBalances(String label) throws SQLException {
        try (Connection connection = DatabaseConnectionFactory.openConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("""
                     SELECT account_number, holder_name, balance
                     FROM payment_accounts
                     ORDER BY id
                     """)) {
            BigDecimal total = BigDecimal.ZERO;

            System.out.println(label + ":");
            while (resultSet.next()) {
                BigDecimal balance = resultSet.getBigDecimal("balance");
                total = total.add(balance);
                System.out.println("  " + resultSet.getString("account_number")
                        + " | " + resultSet.getString("holder_name")
                        + " | balance=" + balance);
            }

            System.out.println("  Total balance across both accounts = " + total);
        }
    }
}
