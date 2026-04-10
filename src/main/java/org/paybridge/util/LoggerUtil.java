package org.paybridge.util;

public class LoggerUtil {

    public static void logParsedField(
            String mti,
            String bitmapHex,
            String bitmapBinary,
            int fieldNumber,
            String fieldName,
            int fieldLength,
            String value,
            int pointer
    ) {

        System.out.println("MTI: " + mti);
        System.out.println("Bitmap Hex: " + bitmapHex);
        System.out.println("Bitmap Binary: " + bitmapBinary);
        System.out.println("Parsed Field: " + fieldNumber);
        System.out.println("Field Name: " + fieldName);
        System.out.println("Field Length: " + fieldLength);
        System.out.println("Value: " + value);
        System.out.println("Pointer: " + pointer);
        System.out.println();
    }
}