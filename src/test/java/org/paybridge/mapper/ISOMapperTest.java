package org.paybridge.mapper;

import org.junit.jupiter.api.Test;
import org.paybridge.dto.ISOResponse;
import org.paybridge.model.ISOMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ISOMapperTest {

    @Test
    void mapsMatchingFieldsToResponse() {
        ISOMessage message = new ISOMessage();
        message.setMti("0200");
        message.setPan("1234567890123456");
        message.setProcessingCode("000000");

        ISOMapper mapper = new ISOMapper();
        ISOResponse response = mapper.map(message, ISOResponse.class);

        assertEquals("0200", response.getMti());
        assertEquals("1234567890123456", response.getPan());
        assertEquals("000000", response.getProcessingCode());
    }

    @Test
    void throwsRuntimeExceptionWhenTargetCannotBeInstantiated() {
        ISOMessage message = new ISOMessage();
        ISOMapper mapper = new ISOMapper();

        assertThrows(RuntimeException.class, () -> mapper.map(message, NoDefaultConstructor.class));
    }

    private static class NoDefaultConstructor {
        private NoDefaultConstructor(String value) {
        }
    }
}
