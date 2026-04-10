package org.paybridge.annotation;

import org.paybridge.enums.LengthType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface ISOField {

    int fieldNumber();

    int length() default 0;

    LengthType type() default LengthType.FIXED;

}