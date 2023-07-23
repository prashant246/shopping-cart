package com.shopping.cart.datatypes.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserResponse {
    private String userId;
    private LoginResponse loginDetail;
}
