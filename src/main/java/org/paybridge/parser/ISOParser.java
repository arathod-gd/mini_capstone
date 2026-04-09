package org.paybridge.parser;

import org.paybridge.annotation.ISOField;
import org.paybridge.model.ISOMessage;

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.util.Arrays;

public class ISOParser {

    public ISOMessage parse(String input) throws Exception {

        /*
         * STEP 1:
         * Validate Input
         */
        if (input == null || input.length() < 20) {
            throw new RuntimeException("Invalid ISO Message: Input too short");
        }

        ISOMessage isoMessage = new ISOMessage();


        /*
         * STEP 2:
         * Parse MTI
         */
        String mti = input.substring(0, 4);
        isoMessage.setMti(mti);


        /*
         * STEP 3:
         * Parse Bitmap HEX
         */
        String bitmapHex = input.substring(4, 20);


        /*
         * STEP 4:
         * Convert Bitmap HEX -> Binary
         */
        String bitmapBinary = convertHexToBinary(bitmapHex);


        /*
         * STEP 5:
         * Start pointer after MTI + Bitmap
         */
        int pointer = 20;


        /*
         * STEP 6:
         * Get All Fields From Model
         */
        Field[] fields = ISOMessage.class.getDeclaredFields();


        /*
         * STEP 7:
         * Sort Fields By fieldNumber
         */
        Arrays.sort(fields, (a, b) -> {

            ISOField annoA = a.getAnnotation(ISOField.class);
            ISOField annoB = b.getAnnotation(ISOField.class);

            if (annoA == null || annoB == null) {
                return 0;
            }

            return Integer.compare(
                    annoA.fieldNumber(),
                    annoB.fieldNumber()
            );
        });


        /*
         * STEP 8:
         * Loop Through Each Annotated Field
         */
        for (Field field : fields) {

            if (!field.isAnnotationPresent(ISOField.class)) {
                continue;
            }

            ISOField annotation = field.getAnnotation(ISOField.class);

            int fieldNumber = annotation.fieldNumber();
            int length = annotation.length();


            /*
             * STEP 9:
             * Validate Field Number
             */
            validateFieldNumber(fieldNumber);


            /*
             * STEP 10:
             * Check Bitmap If Field Exists
             */
            if (isFieldPresent(bitmapBinary, fieldNumber)) {


                /*
                 * STEP 11:
                 * Validate Pointer Range
                 */
                if (pointer + length > input.length()) {
                    throw new RuntimeException(
                            "Input too short for field: " + fieldNumber
                    );
                }


                /*
                 * STEP 12:
                 * Extract Value
                 */
                String value = input.substring(pointer, pointer + length);


                /*
                 * STEP 13:
                 * Move Pointer Forward
                 */
                pointer += length;


                /*
                 * STEP 14:
                 * Set Value Using Reflection
                 */
                field.setAccessible(true);
                field.set(isoMessage, value);


                /*
                 * DEBUG LOGS
                 */
//                System.out.println("Parsed Field: " + fieldNumber);
//                System.out.println("Value: " + value);
//                System.out.println("Pointer: " + pointer);
            }
        }

        return isoMessage;
    }


    /*
     * Helper Method:
     * Convert HEX -> Binary
     */
    private String convertHexToBinary(String hex) {


        /*
         * Using 64 bit because ISO8583 Primary bitmap is 64 bit and hex length 16
         * secondary bitmap is also 64 bit with hex length 32, here we are only parsing primary bitmap in this example
         * Tertiary bitmap is also 64 bit with hex length 48
         */

        return String.format("%64s",
                        new BigInteger(hex, 16).toString(2))
                .replace(' ', '0');
    }


    /*
     * Helper Method:
     * Check If Bitmap Field Exists
     */
    private boolean isFieldPresent(String bitmapBinary, int fieldNumber) {

        return bitmapBinary.charAt(fieldNumber - 1) == '1';
    }


    /*
     * Helper Method:
     * Validate Field Number Range
     */
    private void validateFieldNumber(int fieldNumber) {

        if (fieldNumber < 1 || fieldNumber > 64) {
            throw new RuntimeException(
                    "Invalid ISO Field Number: " + fieldNumber
            );
        }
    }
}