package org.paybridge;
import org.paybridge.json.JsonConvert;
import org.paybridge.model.ISOMessage;
import org.paybridge.parser.ISOParser;

public class Main {

    public static void main(String[] args) throws Exception {

//        String input =
//                "020072380000008080001612345678901234560000000000010000040912304512345612304504091225123456TERMID01MERCHANT000001";

       String input = "020070000000000000001234567890123456000000000000010000";
        ISOParser parser = new ISOParser();

        ISOMessage message = parser.parse(input);
        System.out.println(JsonConvert.toJson(message));
    }
}
