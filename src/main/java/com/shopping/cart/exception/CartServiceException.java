package com.shopping.cart.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CartServiceException extends RuntimeException{
    private String message;
    private HttpStatus httpStatus;

    public CartServiceException(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
