package org.paybridge.parser;

import org.junit.jupiter.api.Test;
import org.paybridge.exceptions.ISOParserException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ISOParserErrorTest {

    private final ISOParser parser = new ISOParser();

    @Test
    void throwsForInvalidBitmap() {
        ISOParserException exception = assertThrows(
                ISOParserException.class,
                () -> parser.parse("0200Z000000000000000")
        );

        assertEquals("INVALID_BITMAP", exception.getErrorCode());
    }

    @Test
    void throwsForInvalidLlvarHeader() {
        ISOParserException exception = assertThrows(
                ISOParserException.class,
                () -> parser.parse("02004000000000000000AB1234")
        );

        assertEquals("INVALID_LENGTH_FORMAT", exception.getErrorCode());
    }

    @Test
    void throwsForOversizedLlvarValue() {
        ISOParserException exception = assertThrows(
                ISOParserException.class,
                () -> parser.parse("020040000000000000002012345678901234567890")
        );

        assertEquals("INVALID_FIELD_LENGTH", exception.getErrorCode());
    }
}
