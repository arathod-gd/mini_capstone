package org.paybridge.annotation;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface ISOField {

    int fieldNumber();

    int length() default 0;
}