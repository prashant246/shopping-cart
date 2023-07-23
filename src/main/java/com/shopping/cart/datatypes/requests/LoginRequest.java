package com.shopping.cart.datatypes.requests;

import lombok.Data;

@Data
public class LoginRequest {
    private String userId;
    private String password;
}
