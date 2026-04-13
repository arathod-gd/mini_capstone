package org.paybridge;

import org.paybridge.db.ParseLogRecord;
import org.paybridge.db.ParseLogRepository;
import org.paybridge.dto.ErrorResponse;
import org.paybridge.exceptions.ISOParserException;
import org.paybridge.json.JsonConvert;
import org.paybridge.mapper.ISOMapper;
import org.paybridge.model.ISOMessage;
import org.paybridge.parser.ISOParser;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        ParseLogRepository parseLogRepository = new ParseLogRepository();
        initializeDatabase(parseLogRepository);

        Scanner scanner = new Scanner(System.in);
        ISOParser parser = new ISOParser();
        ISOMapper mapper = new ISOMapper();

        while(true) {
            System.out.println("Enter ISO 8583 Message or type 'logs' to view saved data or type 'exit' to exit:");
            String input = scanner.nextLine();

            if("exit".equalsIgnoreCase(input.trim())) {
                System.out.println("Exiting application...");
                break;
            }

            if ("logs".equalsIgnoreCase(input.trim())) {
                printLogs(parseLogRepository);
                continue;
            }

            try {
                ISOMessage message = parser.parse(input);

                Object response = mapper.map(message, Class.forName("org.paybridge.dto.ISOResponse"));

                String responseJson = JsonConvert.toJson(response);
                saveLog(parseLogRepository, input, "SUCCESS", responseJson, null);
                System.out.println("data saved sucessfully..");
                System.out.println(responseJson);

            }catch (ISOParserException ex) {
                    ErrorResponse error = new ErrorResponse(
                            ex.getErrorCode(),
                            ex.getMessage(),
                            ex.getFieldNumber()
                    );

                    String errorJson = JsonConvert.toJson(error);
                    saveLog(parseLogRepository, input, "ERROR", errorJson, ex.getErrorCode());
                    System.out.println(errorJson);
                }
        }
    }

    private static void initializeDatabase(ParseLogRepository parseLogRepository) {
        try {
            parseLogRepository.initialize();
            System.out.println("Connected to PostgreSQL and ensured iso_parse_logs exists.");
        } catch (SQLException ex) {
            System.err.println("PostgreSQL is not ready. Messages will not be stored until the database is available: " + ex.getMessage());
        }
    }

    private static void saveLog(ParseLogRepository parseLogRepository,
                                String rawMessage,
                                String status,
                                String payload,
                                String errorCode) {
        try {
            parseLogRepository.save(rawMessage, status, payload, errorCode);
        } catch (SQLException ex) {
            System.err.println("Failed to store parse log in PostgreSQL: " + ex.getMessage());
        }
    }

    private static void printLogs(ParseLogRepository parseLogRepository) {
        try {
            List<ParseLogRecord> records = parseLogRepository.findAll();

            if (records.isEmpty()) {
                System.out.println("No saved records found in iso_parse_logs.");
                return;
            }

            for (ParseLogRecord record : records) {
                System.out.println("----------------------------------------");
                System.out.println("ID: " + record.id());
                System.out.println("Created At: " + record.createdAt());
                System.out.println("Status: " + record.status());
                System.out.println("Error Code: " + (record.errorCode() == null ? "-" : record.errorCode()));
                System.out.println("Raw Message: " + record.rawMessage());
                System.out.println("Payload: " + record.payload());
            }
            System.out.println("----------------------------------------");
        } catch (SQLException ex) {
            System.err.println("Failed to read parse logs from PostgreSQL: " + ex.getMessage());
        }
    }
}
