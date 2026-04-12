package org.paybridge.exceptions;

public class ISOParserException extends RuntimeException {

    private final String errorCode;
    private final String message;
    private final int fieldNumber;

    public ISOParserException(String errorCode, String message, int fieldNumber) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
        this.fieldNumber = fieldNumber;
    }

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public int getFieldNumber() {
        return fieldNumber;
    }
}