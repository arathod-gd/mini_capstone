package org.paybridge.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonConvertTest {

    @Test
    void convertsObjectToPrettyPrintedJson() {
        String json = JsonConvert.toJson(new SampleResponse("ok", 200));

        assertTrue(json.contains("\"message\": \"ok\""));
        assertTrue(json.contains("\"code\": 200"));
    }

    private record SampleResponse(String message, int code) {
    }
}
