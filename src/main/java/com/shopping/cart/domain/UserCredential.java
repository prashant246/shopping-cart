package com.shopping.cart.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserCredential {

    private String userId;
    private String password;
}
