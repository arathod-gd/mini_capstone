package org.paybridge.dto;

public class ErrorResponse {
    private String errorCode;
    private String message;
    private int fieldNumber;

    public ErrorResponse(String errorCode, String message, int fieldNumber) {
        this.errorCode = errorCode;
        this.message = message;
        this.fieldNumber = fieldNumber;
    }
}