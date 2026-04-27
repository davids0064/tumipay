package com.tumipay.application.error;

public enum ErrorCode {
    SUCCESS("000", "Successful operation"),
    VALIDATION("001", "Validation error"),
    INTERNAL("002", "Internal error"),
    NOT_FOUND("003", "Not found"),
    DUPLICATE("004", "Duplicate");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
