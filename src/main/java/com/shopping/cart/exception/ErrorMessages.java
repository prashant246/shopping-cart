package com.shopping.cart.exception;

public enum ErrorMessages {
    INVALID_SESSION_ID("Session Id is invalid"),
    SUSPENDED_USER("User is suspended"),
    AUTHORIZED_FOR_ADMIN("Only Admin are authorized to perform this action"),
    INVALID_PASSWORD("Password is invalid"),
    PRODUCT_ID_NOT_PRESENT("Product Id is not present"),
    INVALID_USER_ID("User Id is invalid");

    ErrorMessages(String s) {
        this.errorMessage = s;
    }

    private String errorMessage;

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
