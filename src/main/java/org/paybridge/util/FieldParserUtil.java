package org.paybridge.util;

import org.paybridge.enums.LengthType;
import org.paybridge.exceptions.ISOParserException;

public class FieldParserUtil {

    /*
     * Parse field value depending on type.
     */
    public static String parseValue(
            String input,
            int pointer,
            int fixedLength,
            LengthType type
    ) {

        if (type == LengthType.FIXED) {

            validateBounds(input, pointer, fixedLength, "FIXED");

            return input.substring(pointer, pointer + fixedLength);
        }

        if (type == LengthType.LLVAR) {

            int length = parseLength(input, pointer, 2, "LLVAR");
            validateMaxLength(length, fixedLength, "LLVAR");

            validateBounds(input, pointer + 2, length, "LLVAR");

            return input.substring(pointer + 2, pointer + 2 + length);
        }

        if (type == LengthType.LLLVAR) {

            int length = parseLength(input, pointer, 3, "LLLVAR");
            validateMaxLength(length, fixedLength, "LLLVAR");

            validateBounds(input, pointer + 3, length, "LLLVAR");

            return input.substring(pointer + 3, pointer + 3 + length);
        }

        throw new ISOParserException(
                "UNSUPPORTED_FIELD_TYPE",
                "Unsupported Field Type: " + type,
                -1
        );
    }

    /*
     * Move pointer after parsing field.
     */
    public static int movePointer(
            String input,
            int pointer,
            int fixedLength,
            LengthType type
    ) {

        if (type == LengthType.FIXED) {

            validateBounds(input, pointer, fixedLength, "FIXED");

            return pointer + fixedLength;
        }

        if (type == LengthType.LLVAR) {

            int length = parseLength(input, pointer, 2, "LLVAR");
            validateMaxLength(length, fixedLength, "LLVAR");

            return pointer + 2 + length;
        }

        if (type == LengthType.LLLVAR) {

            int length = parseLength(input, pointer, 3, "LLLVAR");
            validateMaxLength(length, fixedLength, "LLLVAR");

            return pointer + 3 + length;
        }

        throw new ISOParserException(
                "UNSUPPORTED_FIELD_TYPE",
                "Unsupported Field Type: " + type,
                -1
        );
    }

    /*
     * Safe length parsing
     */
    private static int parseLength(
            String input,
            int pointer,
            int digits,
            String type
    ) {

        if (pointer + digits > input.length()) {
            throw new ISOParserException(
                    "FIELD_LENGTH_HEADER_TOO_SHORT",
                    type + " length header is invalid or incomplete",
                    -1
            );
        }

        String lengthStr = input.substring(pointer, pointer + digits);

        try {
            return Integer.parseInt(lengthStr);
        } catch (NumberFormatException ex) {
            throw new ISOParserException(
                    "INVALID_LENGTH_FORMAT",
                    type + " length is not numeric: " + lengthStr,
                    -1
            );
        }
    }

    public static int getConsumedLength(
            String input,
            int pointer,
            int fixedLength,
            LengthType type
    ) {

        if (type == LengthType.FIXED) {
            return fixedLength;
        }

        if (type == LengthType.LLVAR) {
            int length = parseLength(input, pointer, 2, "LLVAR");
            validateMaxLength(length, fixedLength, "LLVAR");
            return 2 + length;
        }

        if (type == LengthType.LLLVAR) {
            int length = parseLength(input, pointer, 3, "LLLVAR");
            validateMaxLength(length, fixedLength, "LLLVAR");
            return 3 + length;
        }

        throw new ISOParserException(
                "UNSUPPORTED_FIELD_TYPE",
                "Unsupported Field Type: " + type,
                -1
        );
    }

    /*
     * Safe boundary check
     */
    private static void validateBounds(
            String input,
            int start,
            int length,
            String type
    ) {

        if (start + length > input.length()) {
            throw new ISOParserException(
                    "FIELD_TOO_SHORT",
                    type + " field exceeds input length",
                    -1
            );
        }
    }

    private static void validateMaxLength(
            int actualLength,
            int maxLength,
            String type
    ) {

        if (actualLength > maxLength) {
            throw new ISOParserException(
                    "INVALID_FIELD_LENGTH",
                    type + " field length " + actualLength + " exceeds max allowed " + maxLength,
                    -1
            );
        }
    }
}
