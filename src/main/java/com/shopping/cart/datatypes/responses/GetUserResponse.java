package com.shopping.cart.datatypes.responses;

import com.shopping.cart.datatypes.enums.Role;
import com.shopping.cart.datatypes.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetUserResponse {
    private String name;
    private Status status;
    private Role role;
}
