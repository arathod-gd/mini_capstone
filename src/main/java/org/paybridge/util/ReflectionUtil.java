package org.paybridge.util;

import org.paybridge.annotation.ISOField;

import java.lang.reflect.Field;
import java.util.Arrays;

public class ReflectionUtil {
    /*
     * Get Sorted ISO Fields Based on fieldNumber
     */
    public static Field[] getSortedISOFields(Class<?> clazz) {

        Field[] fields = clazz.getDeclaredFields();

        Arrays.sort(fields, (a, b) -> {

            ISOField annoA = a.getAnnotation(ISOField.class);
            ISOField annoB = b.getAnnotation(ISOField.class);

            int fieldA =
                    (annoA != null)
                            ? annoA.fieldNumber()
                            : Integer.MAX_VALUE;

            int fieldB =
                    (annoB != null)
                            ? annoB.fieldNumber()
                            : Integer.MAX_VALUE;

            return Integer.compare(fieldA, fieldB);
        });

        return fields;
    }
}
