package com.shopping.cart.datatypes.requests;

import com.shopping.cart.datatypes.enums.Role;
import lombok.Data;

@Data
public class CreateOrUpdateUserRequest {

    private String userId;
    private String password;
    private String name;
    private Role role;
}
