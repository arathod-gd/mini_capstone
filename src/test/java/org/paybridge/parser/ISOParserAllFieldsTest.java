package org.paybridge.parser;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.paybridge.annotation.ISOField;
import org.paybridge.enums.LengthType;
import org.paybridge.model.ISOMessage;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ISOParserAllFieldsTest {

    private static final String MTI = "0200";

    @TestFactory
    Stream<DynamicTest> parsesEveryPrimaryBitmapFieldIndividually() {
        ISOParser parser = new ISOParser();

        return Arrays.stream(ISOMessage.class.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(ISOField.class))
                .map(field -> DynamicTest.dynamicTest(
                        "parses field " + field.getAnnotation(ISOField.class).fieldNumber() + " - " + field.getName(),
                        () -> assertSingleFieldParsing(parser, field)
                ));
    }

    private void assertSingleFieldParsing(ISOParser parser, Field targetField) throws Exception {
        ISOField annotation = targetField.getAnnotation(ISOField.class);
        int fieldNumber = annotation.fieldNumber();
        String expectedValue = buildFieldValue(fieldNumber, annotation);
        String message = MTI + buildPrimaryBitmapHex(fieldNumber) + encodeField(expectedValue, annotation.type());

        ISOMessage parsedMessage = parser.parse(message);

        assertNotNull(parsedMessage);
        assertEquals(MTI, parsedMessage.getMti());

        targetField.setAccessible(true);
        assertEquals(expectedValue, targetField.get(parsedMessage));
    }

    private String buildPrimaryBitmapHex(int fieldNumber) {
        StringBuilder bitmap = new StringBuilder("0".repeat(64));
        bitmap.setCharAt(fieldNumber - 1, '1');
        return String.format("%016X", new BigInteger(bitmap.toString(), 2));
    }

    private String encodeField(String value, LengthType type) {
        if (type == LengthType.LLVAR) {
            return String.format("%02d%s", value.length(), value);
        }

        if (type == LengthType.LLLVAR) {
            return String.format("%03d%s", value.length(), value);
        }

        return value;
    }

    private String buildFieldValue(int fieldNumber, ISOField annotation) {
        int maxLength = annotation.length();

        if (annotation.type() == LengthType.LLVAR) {
            return sampleValue(fieldNumber, Math.min(maxLength, 12));
        }

        if (annotation.type() == LengthType.LLLVAR) {
            return sampleValue(fieldNumber, Math.min(maxLength, 15));
        }

        return sampleValue(fieldNumber, maxLength);
    }

    private String sampleValue(int fieldNumber, int length) {
        String seed = String.format("%02d", fieldNumber) + "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder value = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            value.append(seed.charAt(i % seed.length()));
        }

        return value.toString();
    }
}
