package org.paybridge.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ISOParserExceptionTest {

    @Test
    void exposesErrorMetadata() {
        ISOParserException exception = new ISOParserException("INVALID_BITMAP", "Bitmap contains non-hex characters", 1);

        assertEquals("INVALID_BITMAP", exception.getErrorCode());
        assertEquals("Bitmap contains non-hex characters", exception.getMessage());
        assertEquals(1, exception.getFieldNumber());
    }
}
