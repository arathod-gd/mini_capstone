package org.paybridge.util;

public class ValidationUtil {
    /*
     * Validate Input Length
     */
    public static void validateInput(String input) {

        if (input == null || input.length() < 20) {
            throw new RuntimeException(
                    "Invalid ISO Message: Input too short"
            );
        }
    }


    /*
     * Validate ISO Field Number
     */
    public static void validateFieldNumber(int fieldNumber) {

        if (fieldNumber < 1 || fieldNumber > 64) {
            throw new RuntimeException(
                    "Invalid ISO Field Number: " + fieldNumber
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
            throw new RuntimeException(
                    "Input too short for field: " + fieldNumber
            );
        }
    }
}
