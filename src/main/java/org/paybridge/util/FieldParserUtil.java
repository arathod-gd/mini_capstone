package org.paybridge.util;
import org.paybridge.enums.LengthType;
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
            return input.substring(pointer, pointer + fixedLength);
        }

        if (type == LengthType.LLVAR) {
            int dynamicLength = Integer.parseInt(
                    input.substring(pointer, pointer + 2)
            );

            return input.substring(
                    pointer + 2,
                    pointer + 2 + dynamicLength
            );
        }

        if (type == LengthType.LLLVAR) {
            int dynamicLength = Integer.parseInt(
                    input.substring(pointer, pointer + 3)
            );

            return input.substring(
                    pointer + 3,
                    pointer + 3 + dynamicLength
            );
        }

        throw new RuntimeException("Unsupported Field Type");
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
            return pointer + fixedLength;
        }

        if (type == LengthType.LLVAR) {
            int dynamicLength = Integer.parseInt(
                    input.substring(pointer, pointer + 2)
            );

            return pointer + 2 + dynamicLength;
        }

        if (type == LengthType.LLLVAR) {
            int dynamicLength = Integer.parseInt(
                    input.substring(pointer, pointer + 3)
            );

            return pointer + 3 + dynamicLength;
        }

        throw new RuntimeException("Unsupported Field Type");
    }
}