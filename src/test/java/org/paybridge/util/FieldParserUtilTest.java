package org.paybridge.util;

import org.junit.jupiter.api.Test;
import org.paybridge.enums.LengthType;
import org.paybridge.exceptions.ISOParserException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FieldParserUtilTest {

    @Test
    void parsesFixedAndVariableLengthValues() {
        assertEquals("ABCDEF", FieldParserUtil.parseValue("ABCDEF", 0, 6, LengthType.FIXED));
        assertEquals("HELLO", FieldParserUtil.parseValue("05HELLO", 0, 10, LengthType.LLVAR));
        assertEquals("PAYLOAD", FieldParserUtil.parseValue("007PAYLOAD", 0, 20, LengthType.LLLVAR));
    }

    @Test
    void movesPointerForFixedAndVariableLengthFields() {
        assertEquals(6, FieldParserUtil.movePointer("ABCDEF", 0, 6, LengthType.FIXED));
        assertEquals(7, FieldParserUtil.movePointer("05HELLO", 0, 10, LengthType.LLVAR));
        assertEquals(10, FieldParserUtil.movePointer("007PAYLOAD", 0, 20, LengthType.LLLVAR));
    }

    @Test
    void returnsConsumedLengthForFixedAndVariableLengthFields() {
        assertEquals(6, FieldParserUtil.getConsumedLength("ABCDEF", 0, 6, LengthType.FIXED));
        assertEquals(7, FieldParserUtil.getConsumedLength("05HELLO", 0, 10, LengthType.LLVAR));
        assertEquals(10, FieldParserUtil.getConsumedLength("007PAYLOAD", 0, 20, LengthType.LLLVAR));
    }

    @Test
    void throwsWhenLengthHeaderIsIncomplete() {
        ISOParserException exception = assertThrows(
                ISOParserException.class,
                () -> FieldParserUtil.parseValue("0", 0, 10, LengthType.LLVAR)
        );

        assertEquals("FIELD_LENGTH_HEADER_TOO_SHORT", exception.getErrorCode());
    }

    @Test
    void throwsWhenLengthHeaderIsNotNumeric() {
        ISOParserException exception = assertThrows(
                ISOParserException.class,
                () -> FieldParserUtil.parseValue("ABHELLO", 0, 10, LengthType.LLVAR)
        );

        assertEquals("INVALID_LENGTH_FORMAT", exception.getErrorCode());
    }

    @Test
    void throwsWhenValueExceedsMaxLength() {
        ISOParserException exception = assertThrows(
                ISOParserException.class,
                () -> FieldParserUtil.parseValue("12HELLOWORLD!!", 0, 10, LengthType.LLVAR)
        );

        assertEquals("INVALID_FIELD_LENGTH", exception.getErrorCode());
    }

    @Test
    void throwsWhenFieldExceedsInputLength() {
        ISOParserException exception = assertThrows(
                ISOParserException.class,
                () -> FieldParserUtil.parseValue("06HEL", 0, 10, LengthType.LLVAR)
        );

        assertEquals("FIELD_TOO_SHORT", exception.getErrorCode());
    }

    @Test
    void throwsForUnsupportedFieldType() {
        ISOParserException exception = assertThrows(
                ISOParserException.class,
                () -> FieldParserUtil.parseValue("ABCDEF", 0, 6, null)
        );

        assertEquals("UNSUPPORTED_FIELD_TYPE", exception.getErrorCode());
    }
}
