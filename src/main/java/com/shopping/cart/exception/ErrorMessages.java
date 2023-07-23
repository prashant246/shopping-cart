package com.shopping.cart.exception;

public enum ErrorMessages {
    INVALID_SESSION_ID("Session Id is invalid"),
    INVALID_PASSWORD("Password is invalid"),
    INVALID_USER_ID("User Id is invalid");

    ErrorMessages(String s) {
        this.errorMessage = s;
    }

    private String errorMessage;

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
