package org.paybridge.util;

import org.junit.jupiter.api.Test;
import org.paybridge.annotation.ISOField;
import org.paybridge.enums.LengthType;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReflectionUtilTest {

    @Test
    void sortsAnnotatedFieldsByFieldNumberAndLeavesUnannotatedLast() {
        Field[] fields = ReflectionUtil.getSortedISOFields(SampleFields.class);

        assertEquals("first", fields[0].getName());
        assertEquals("second", fields[1].getName());
        assertEquals("unannotated", fields[2].getName());
    }

    private static class SampleFields {
        @ISOField(fieldNumber = 5, length = 2, type = LengthType.FIXED)
        private String second;

        private String unannotated;

        @ISOField(fieldNumber = 2, length = 2, type = LengthType.FIXED)
        private String first;
    }
}
