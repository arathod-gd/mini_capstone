package org.paybridge.util;

import java.math.BigInteger;

public class BitmapUtil {


    /*
     * Convert HEX Bitmap to 64-bit Binary
     */
    public static String convertHexToBinary(String hex) {

        /*
         * Using 64 bit because ISO8583 Primary bitmap is 64 bit and hex length 16
         * secondary bitmap is also 64 bit with hex length 32, here we are only parsing primary bitmap in this example
         * Tertiary bitmap is also 64 bit with hex length 48
         *
         * total count (64+64+64=192) bit with hex length 16+32+48=96, we can easily handle all 3 bitmaps with this method by changing the length and hex input accordingly
         */

        return String.format("%64s",
                        new BigInteger(hex, 16).toString(2))
                .replace(' ', '0');
    }


    /*
     * Check if Field Exists in Bitmap
     * String in java starts from 0, so here we're subtracting 1 from fieldNumber to get correct index in bitmapBinary string
     * For example, if fieldNumber is 3, we need to check the 2nd index (0-based) in bitmapBinary string to see if it's '1' or '0'
     */
    public static boolean isFieldPresent(String bitmapBinary, int fieldNumber) {

        return bitmapBinary.charAt(fieldNumber - 1) == '1';
    }
}
