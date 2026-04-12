package org.paybridge;

import org.paybridge.dto.ErrorResponse;
import org.paybridge.exceptions.ISOParserException;
import org.paybridge.json.JsonConvert;
import org.paybridge.mapper.ISOMapper;
import org.paybridge.model.ISOMessage;
import org.paybridge.parser.ISOParser;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Enter ISO 8583 Message:");
            String input = scanner.nextLine();

            // 1. Parse ISO string → ISOMessage
            ISOParser parser = new ISOParser();

            try {
                ISOMessage message = parser.parse(input);

                // 2. Map ISOMessage → ISOResponse (DYNAMIC MAPPER)
                ISOMapper mapper = new ISOMapper();
                Object response = mapper.map(message, Class.forName("org.paybridge.dto.ISOResponse"));

                // 3. Convert response → JSON
                System.out.println(JsonConvert.toJson(response));

            }catch (ISOParserException ex) {
                    ErrorResponse error = new ErrorResponse(
                            ex.getErrorCode(),
                            ex.getMessage(),
                            ex.getFieldNumber()
                    );

                    System.out.println(JsonConvert.toJson(error));
                }
        }
    }
}