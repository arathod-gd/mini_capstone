package org.paybridge.util;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LoggerUtilTest {

    @Test
    void printsParsedFieldDetails() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        System.setOut(new PrintStream(buffer));

        try {
            LoggerUtil.logParsedField("0200", "3020000000000000", "0011", 3, "processingCode", 6, "000000", 26);
        } finally {
            System.setOut(originalOut);
        }

        String output = buffer.toString(StandardCharsets.UTF_8);
        assertTrue(output.contains("MTI: 0200"));
        assertTrue(output.contains("Parsed Field: 3"));
        assertTrue(output.contains("Value: 000000"));
    }
}
