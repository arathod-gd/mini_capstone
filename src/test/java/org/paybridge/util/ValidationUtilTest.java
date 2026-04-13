package org.paybridge.util;

import org.junit.jupiter.api.Test;
import org.paybridge.exceptions.ISOParserException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidationUtilTest {

    @Test
    void acceptsValidInputAndFieldMetadata() {
        assertDoesNotThrow(() -> ValidationUtil.validateInput("02003020000000000000"));
        assertDoesNotThrow(() -> ValidationUtil.validateFieldNumber(1));
        assertDoesNotThrow(() -> ValidationUtil.validateFieldNumber(64));
        assertDoesNotThrow(() -> ValidationUtil.validatePointer(20, 4, 24, 3));
    }

    @Test
    void rejectsInvalidInput() {
        ISOParserException exception = assertThrows(
                ISOParserException.class,
                () -> ValidationUtil.validateInput("short")
        );

        assertEquals("INVALID_MESSAGE", exception.getErrorCode());
    }

    @Test
    void rejectsInvalidFieldNumber() {
        ISOParserException exception = assertThrows(
                ISOParserException.class,
                () -> ValidationUtil.validateFieldNumber(0)
        );

        assertEquals("INVALID_FIELD_NUMBER", exception.getErrorCode());
        assertEquals(0, exception.getFieldNumber());
    }

    @Test
    void rejectsOutOfRangePointer() {
        ISOParserException exception = assertThrows(
                ISOParserException.class,
                () -> ValidationUtil.validatePointer(20, 5, 24, 4)
        );

        assertEquals("FIELD_TOO_SHORT", exception.getErrorCode());
        assertEquals(4, exception.getFieldNumber());
    }
}
