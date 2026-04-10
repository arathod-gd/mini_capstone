//package org.paybridge.parser;
//
//import org.paybridge.annotation.ISOField;
//import org.paybridge.constants.ISOConstants;
//import org.paybridge.model.ISOMessage;
//import org.paybridge.util.BitmapUtil;
//import org.paybridge.util.LoggerUtil;
//import org.paybridge.util.ReflectionUtil;
//import org.paybridge.util.ValidationUtil;
//
//import java.lang.reflect.Field;
//
//public class ISOParser {
//
//    public ISOMessage parse(String input) throws Exception {
//
//        /*
//         * =====================================================
//         * STEP 1:
//         * Validate Incoming Input Message
//         * =====================================================
//         *
//         * Checks:
//         * - input should not be null
//         * - minimum length should be enough for MTI + Bitmap
//         */
//        ValidationUtil.validateInput(input);
//
//
//        /*
//         * =====================================================
//         * STEP 2:
//         * Create Empty ISOMessage Object
//         * =====================================================
//         *
//         * This object will store parsed field values.
//         */
//        ISOMessage isoMessage = new ISOMessage();
//
//
//        /*
//         * =====================================================
//         * STEP 3:
//         * Extract MTI (Message Type Indicator)
//         * =====================================================
//         *
//         * MTI is first 4 characters of ISO message.
//         *
//         * Example:
//         * 0200
//         */
//        String mti = input.substring(
//                0,
//                ISOConstants.MTI_LENGTH
//        );
//
//        isoMessage.setMti(mti);
//
//
//        /*
//         * =====================================================
//         * STEP 4:
//         * Extract Primary Bitmap HEX
//         * =====================================================
//         *
//         * Bitmap starts after MTI.
//         *
//         * MTI Length = 4
//         * Bitmap HEX Length = 16
//         */
//        String bitmapHex = input.substring(
//                ISOConstants.MTI_LENGTH,
//                ISOConstants.MTI_LENGTH +
//                        ISOConstants.PRIMARY_BITMAP_HEX_LENGTH
//        );
//
//
//        /*
//         * =====================================================
//         * STEP 5:
//         * Convert Bitmap HEX -> Binary
//         * =====================================================
//         *
//         * Example:
//         * 7238000000808000
//         *
//         * Converts into:
//         * 0111001000111000000000000000000000000000100000001000000000000000
//         */
//        String bitmapBinary =
//                BitmapUtil.convertHexToBinary(bitmapHex);
//
//
//        /*
//         * =====================================================
//         * STEP 6:
//         * Initialize Pointer
//         * =====================================================
//         *
//         * Pointer tells parser from where actual field data starts.
//         *
//         * After:
//         * MTI (4) + Bitmap (16)
//         *
//         * Pointer starts at 20.
//         */
//        int pointer =
//                ISOConstants.MTI_LENGTH +
//                        ISOConstants.PRIMARY_BITMAP_HEX_LENGTH;
//
//
//        /*
//         * =====================================================
//         * STEP 7:
//         * Fetch Sorted ISO Fields Using Reflection
//         * =====================================================
//         *
//         * Reflection reads all declared fields from ISOMessage model
//         * and sorts them by annotation fieldNumber.
//         */
//        Field[] fields =
//                ReflectionUtil.getSortedISOFields(
//                        ISOMessage.class
//                );
//
//
//        /*
//         * =====================================================
//         * STEP 8:
//         * Loop Through All Model Fields
//         * =====================================================
//         */
//        for (Field field : fields) {
//
//
//            /*
//             * Skip if field has no ISOField annotation.
//             */
//            if (!field.isAnnotationPresent(ISOField.class)) {
//                continue;
//            }
//
//
//            /*
//             * =====================================================
//             * STEP 9:
//             * Read Annotation Values
//             * =====================================================
//             */
//            ISOField annotation =
//                    field.getAnnotation(ISOField.class);
//
//            int fieldNumber =
//                    annotation.fieldNumber();
//
//            int fieldLength =
//                    annotation.length();
//
//
//            /*
//             * =====================================================
//             * STEP 10:
//             * Validate Field Number
//             * =====================================================
//             */
//            ValidationUtil.validateFieldNumber(fieldNumber);
//
//
//            /*
//             * =====================================================
//             * STEP 11:
//             * Check Bitmap If Field Exists
//             * =====================================================
//             *
//             * If bitmap bit = 1
//             * means field is present.
//             *
//             * If bitmap bit = 0
//             * skip field.
//             */
//            if (BitmapUtil.isFieldPresent(
//                    bitmapBinary,
//                    fieldNumber
//            )) {
//
//
//                /*
//                 * =====================================================
//                 * STEP 12:
//                 * Validate Pointer Range
//                 * =====================================================
//                 *
//                 * Ensure parser doesn't go beyond input length.
//                 */
//                ValidationUtil.validatePointer(
//                        pointer,
//                        fieldLength,
//                        input.length(),
//                        fieldNumber
//                );
//
//
//                /*
//                 * =====================================================
//                 * STEP 13:
//                 * Extract Field Value
//                 * =====================================================
//                 *
//                 * Example:
//                 * pointer = 20
//                 * length = 6
//                 *
//                 * substring(20,26)
//                 */
//                String value = input.substring(
//                        pointer,
//                        pointer + fieldLength
//                );
//
//
//                /*
//                 * =====================================================
//                 * STEP 14:
//                 * Move Pointer Forward
//                 * =====================================================
//                 *
//                 * Pointer shifts after extracted field.
//                 */
//                pointer += fieldLength;
//
//
//                /*
//                 * =====================================================
//                 * STEP 15:
//                 * Set Parsed Value in ISOMessage Object
//                 * =====================================================
//                 *
//                 * Reflection dynamically sets value.
//                 */
//                field.setAccessible(true);
//
//                field.set(
//                        isoMessage,
//                        value
//                );
//
//
//                /*
//                 * =====================================================
//                 * STEP 16:
//                 * Debug Logging
//                 * OPTIONAL IF NEEDED FOR DEBUGGING
//                 * =====================================================
//                 */
//                LoggerUtil.logParsedField(
//                        mti,
//                        bitmapHex,
//                        bitmapBinary,
//                        fieldNumber,
//                        field.getName(),
//                        fieldLength,
//                        value,
//                        pointer
//                );
//            }
//        }
//
//
//        /*
//         * =====================================================
//         * STEP 17:
//         * Return Fully Parsed Object
//         * =====================================================
//         */
//        return isoMessage;
//    }
//}

package org.paybridge.parser;

import org.paybridge.annotation.ISOField;
import org.paybridge.enums.LengthType;
import org.paybridge.model.ISOMessage;
import org.paybridge.util.*;

import java.lang.reflect.Field;

public class ISOParser {

    public ISOMessage parse(String input) throws Exception {

        // Step 1: Validate full input first
        ValidationUtil.validateInput(input);

        ISOMessage isoMessage = new ISOMessage();

        // Step 2: Extract MTI
        String mti = input.substring(0, 4);
        isoMessage.setMti(mti);

        // Step 3: Extract Bitmap
        String bitmapHex = input.substring(4, 20);
        String bitmapBinary = BitmapUtil.convertHexToBinary(bitmapHex);

        // Step 4: Pointer starts after MTI + Bitmap
        int pointer = 20;

        // Step 5: Get sorted fields
        Field[] fields = ReflectionUtil.getSortedISOFields(
                ISOMessage.class
        );

        // Step 6: Parse every annotated field
        for (Field field : fields) {

            if (!field.isAnnotationPresent(ISOField.class)) {
                continue;
            }

            ISOField annotation = field.getAnnotation(ISOField.class);

            int fieldNumber = annotation.fieldNumber();
            int fieldLength = annotation.length();
            LengthType LengthType = annotation.type();

            ValidationUtil.validateFieldNumber(fieldNumber);

            // Skip if field not present in bitmap
            if (!BitmapUtil.isFieldPresent(bitmapBinary, fieldNumber)) {
                continue;
            }

            // Validate pointer depending on type
            int consumedLength = fieldLength;

            if (LengthType == LengthType.LLVAR) {
                consumedLength = 2 + Integer.parseInt(
                        input.substring(pointer, pointer + 2)
                );
            }
            else if (LengthType == LengthType.LLLVAR) {
                consumedLength = 3 + Integer.parseInt(
                        input.substring(pointer, pointer + 3)
                );
            }

            ValidationUtil.validatePointer(
                    pointer,
                    consumedLength,
                    input.length(),
                    fieldNumber
            );

            // Parse value
            String value = FieldParserUtil.parseValue(
                    input,
                    pointer,
                    fieldLength,
                    LengthType
            );

            // Move pointer
            pointer = FieldParserUtil.movePointer(
                    input,
                    pointer,
                    fieldLength,
                    LengthType
            );

            // Set value using reflection
            field.setAccessible(true);
            field.set(isoMessage, value);

            LoggerUtil.logParsedField(
                    mti,
                    bitmapHex,
                    bitmapBinary,
                    fieldNumber,
                    field.getName(),
                    consumedLength,
                    value,
                    pointer
            );
        }

        return isoMessage;
    }
}