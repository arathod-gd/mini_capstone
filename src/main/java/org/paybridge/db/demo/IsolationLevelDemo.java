package org.paybridge.db.demo;

import org.paybridge.db.DatabaseConnectionFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public final class IsolationLevelDemo {

    private static final String MERCHANT_ID = "M-DEMO-001";
    private static final LocalDate BUSINESS_DATE = LocalDate.of(2026, 4, 15);

    private IsolationLevelDemo() {
    }

    public static void main(String[] args) throws Exception {
        DemoSchemaManager.initializeSchema();

        runScenario(
                "Default isolation (READ COMMITTED)",
                Connection.TRANSACTION_READ_COMMITTED
        );

        System.out.println();

        runScenario(
                "Correct isolation for a stable settlement snapshot (REPEATABLE READ)",
                Connection.TRANSACTION_REPEATABLE_READ
        );
    }

    private static void runScenario(String label, int isolationLevel) throws Exception {
        DemoSchemaManager.resetIsolationDemoData();

        CountDownLatch firstReadDone = new CountDownLatch(1);
        CountDownLatch insertCommitted = new CountDownLatch(1);
        AtomicInteger firstCount = new AtomicInteger();
        AtomicInteger secondCount = new AtomicInteger();

        Thread reader = new Thread(() -> {
            try (Connection connection = DatabaseConnectionFactory.openConnection()) {
                connection.setAutoCommit(false);
                connection.setTransactionIsolation(isolationLevel);

                firstCount.set(countApprovedEvents(connection));
                firstReadDone.countDown();
                insertCommitted.await();
                secondCount.set(countApprovedEvents(connection));

                connection.commit();
                connection.setAutoCommit(true);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        Thread writer = new Thread(() -> {
            try {
                firstReadDone.await();
                insertConcurrentApprovedEvent();
                insertCommitted.countDown();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        reader.start();
        writer.start();
        reader.join();
        writer.join();

        System.out.println(label);
        System.out.println("  First count  = " + firstCount.get());
        System.out.println("  Second count = " + secondCount.get());
    }

    private static int countApprovedEvents(Connection connection) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("""
                SELECT COUNT(*)
                FROM settlement_events
                WHERE merchant_id = ?
                  AND business_date = ?
                  AND response_code = '00'
                """)) {
            statement.setString(1, MERCHANT_ID);
            statement.setDate(2, Date.valueOf(BUSINESS_DATE));

            try (ResultSet resultSet = statement.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(1);
            }
        }
    }

    private static void insertConcurrentApprovedEvent() throws SQLException {
        try (Connection connection = DatabaseConnectionFactory.openConnection();
             PreparedStatement statement = connection.prepareStatement("""
                     INSERT INTO settlement_events
                     (merchant_id, terminal_id, response_code, amount, business_date)
                     VALUES (?, ?, ?, ?, ?)
                     """)) {
            statement.setString(1, MERCHANT_ID);
            statement.setString(2, "TERM1001");
            statement.setString(3, "00");
            statement.setBigDecimal(4, new BigDecimal("110.00"));
            statement.setDate(5, Date.valueOf(BUSINESS_DATE));
            statement.executeUpdate();
        }
    }
}
