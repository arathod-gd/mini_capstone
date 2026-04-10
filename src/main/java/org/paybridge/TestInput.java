package org.paybridge;

public class TestInput {

    public static void main(String[] args) {

        StringBuilder input = new StringBuilder();

        /*
         * MTI
         */
        input.append("0200");

        /*
         * Primary Bitmap
         * Fields 2–64 ON
         */
        input.append("7FFFFFFFFFFFFFFF");


        /*
         * FIELD 2 → LLVAR
         * 16 digit PAN → prefix = 16
         */
        input.append(toLLVAR("1234567890123456"));

        /*
         * Fixed Fields
         */
        input.append("000000"); // 3
        input.append("000000010000"); // 4
        input.append("000000020000"); // 5
        input.append("000000030000"); // 6
        input.append("0409123045"); // 7
        input.append("00000001"); // 8
        input.append("00000002"); // 9
        input.append("00000003"); // 10
        input.append("123456"); // 11
        input.append("123045"); // 12
        input.append("0409"); // 13
        input.append("1225"); // 14
        input.append("1226"); // 15
        input.append("1227"); // 16
        input.append("1228"); // 17
        input.append("5999"); // 18
        input.append("356"); // 19
        input.append("356"); // 20
        input.append("356"); // 21
        input.append("051"); // 22
        input.append("001"); // 23
        input.append("001"); // 24
        input.append("00"); // 25
        input.append("00"); // 26
        input.append("1"); // 27
        input.append("12345678"); // 28
        input.append("12345678"); // 29
        input.append("12345678"); // 30
        input.append("12345678"); // 31


        /*
         * FIELD 32 → LLVAR
         */
        input.append(toLLVAR("12345678901"));

        /*
         * FIELD 33 → LLVAR
         */
        input.append(toLLVAR("12345678901"));

        /*
         * FIELD 34 → LLVAR
         */
        input.append(toLLVAR("X".repeat(28)));

        /*
         * FIELD 35 → LLVAR
         */
        input.append(toLLVAR("Y".repeat(37)));

        /*
         * FIELD 36 → LLLVAR
         */
        input.append(toLLLVAR("Z".repeat(104)));

        input.append("123456789012"); // 37
        input.append("ABC123"); // 38
        input.append("00"); // 39
        input.append("999"); // 40
        input.append("TERMID01"); // 41
        input.append("MERCHANT0000001"); // 42
        input.append("L".repeat(40)); // 43


        /*
         * FIELD 44 → LLVAR
         */
        input.append(toLLVAR("R".repeat(25)));

        /*
         * FIELD 45 → LLVAR
         */
        input.append(toLLVAR("T".repeat(76)));

        /*
         * FIELD 46 → LLLVAR
         */
        input.append(toLLLVAR("A".repeat(999)));

        /*
         * FIELD 47 → LLLVAR
         */
        input.append(toLLLVAR("B".repeat(999)));

        /*
         * FIELD 48 → LLLVAR
         */
        input.append(toLLLVAR("C".repeat(999)));

        input.append("356"); // 49
        input.append("356"); // 50
        input.append("356"); // 51
        input.append("P".repeat(16)); // 52
        input.append("S".repeat(16)); // 53


        /*
         * FIELD 54 → LLLVAR
         */
        input.append(toLLLVAR("M".repeat(120)));

        /*
         * FIELD 55 → LLLVAR
         */
        input.append(toLLLVAR("E".repeat(255)));

        /*
         * FIELD 56 → LLLVAR
         */
        input.append(toLLLVAR("R".repeat(35)));

        /*
         * FIELD 57–63 → LLLVAR
         */
        input.append(toLLLVAR("N".repeat(999))); //57
        input.append(toLLLVAR("N".repeat(999))); //58
        input.append(toLLLVAR("N".repeat(999))); //59
        input.append(toLLLVAR("P".repeat(999))); //60
        input.append(toLLLVAR("P".repeat(999))); //61
        input.append(toLLLVAR("P".repeat(999))); //62
        input.append(toLLLVAR("P".repeat(999))); //63

        input.append("M".repeat(16)); // 64


        System.out.println(input);
        System.out.println("TOTAL LENGTH = " + input.length());
    }


    /*
     * Converts Value → LLVAR Format
     * Example:
     * HELLO → 05HELLO
     */
    public static String toLLVAR(String value) {
        return String.format("%02d", value.length()) + value;
    }


    /*
     * Converts Value → LLLVAR Format
     * Example:
     * HELLO → 005HELLO
     */
    public static String toLLLVAR(String value) {
        return String.format("%03d", value.length()) + value;
    }
}