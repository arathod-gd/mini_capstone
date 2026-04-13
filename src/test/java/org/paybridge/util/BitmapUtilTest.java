package org.paybridge.util;

import org.junit.jupiter.api.Test;
import org.paybridge.exceptions.ISOParserException;

import static org.junit.jupiter.api.Assertions.*;

class BitmapUtilTest {

    @Test
    void convertsHexBitmapToBinary() {
        String binary = BitmapUtil.convertHexToBinary("3020000000000000");

        assertEquals(64, binary.length());
        assertTrue(BitmapUtil.isFieldPresent(binary, 3));
        assertTrue(BitmapUtil.isFieldPresent(binary, 4));
        assertTrue(BitmapUtil.isFieldPresent(binary, 11));
        assertFalse(BitmapUtil.isFieldPresent(binary, 2));
    }

    @Test
    void throwsForInvalidHexBitmap() {
        ISOParserException exception = assertThrows(
                ISOParserException.class,
                () -> BitmapUtil.convertHexToBinary("Z000000000000000")
        );

        assertEquals("INVALID_BITMAP", exception.getErrorCode());
        assertEquals(1, exception.getFieldNumber());
    }
}
