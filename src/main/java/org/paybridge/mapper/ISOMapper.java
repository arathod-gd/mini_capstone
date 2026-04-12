package org.paybridge.mapper;

import org.paybridge.dto.ISOResponse;
import org.paybridge.model.ISOMessage;

import java.lang.reflect.Field;

public class ISOMapper {

    public ISOResponse map(ISOMessage message, Class<?> aClass) {

        try {
            Object response = aClass.getDeclaredConstructor().newInstance();

            Field[] isoFields = ISOMessage.class.getDeclaredFields();
            Field[] dtoFields = aClass.getDeclaredFields();

            for (Field isoField : isoFields) {

                isoField.setAccessible(true);

                Object value = isoField.get(message);
                if (value == null) continue;

                String fieldName = isoField.getName();

                // find matching field in DTO
                for (Field dtoField : dtoFields) {

                    dtoField.setAccessible(true);

                    if (dtoField.getName().equals(fieldName)) {
                        dtoField.set(response, value);
                        break;
                    }
                }
            }

            return (ISOResponse) response;
        } catch (Exception e) {
            throw new RuntimeException("Mapping failed", e);
        }
    }
}
