package org.paybridge.util;

import org.paybridge.exceptions.ISOParserException;

public class ValidationUtil {
    /*
     * Validate Input Length
     */
    public static void validateInput(String input) {

        if (input == null || input.length() < 20) {
            throw new ISOParserException(
                    "INVALID_MESSAGE",
                    "Invalid ISO Message: Input too short",
                    -1
            );
        }
    }


    /*
     * Validate ISO Field Number
     */
    public static void validateFieldNumber(int fieldNumber) {

        if (fieldNumber < 1 || fieldNumber > 64) {
            throw new ISOParserException(
                    "INVALID_FIELD_NUMBER",
                    "Invalid ISO Field Number: " + fieldNumber,
                    fieldNumber
            );
        }
    }


    /*
     * Validate Pointer Range
     */
    public static void validatePointer(
            int pointer,
            int length,
            int inputLength,
            int fieldNumber
    ) {

        if (pointer + length > inputLength) {
            throw new ISOParserException(
                    "FIELD_TOO_SHORT",
                    "Field " + fieldNumber + " length exceeds input length",
                    fieldNumber
            );
        }
    }
}

