package com.shopping.cart.domain;

import com.shopping.cart.datatypes.enums.Role;
import com.shopping.cart.datatypes.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * User domain is the business entity with minimal details.
 * It will contain basic details of user.
 */

@Data
@Builder
public class User {

    private String name;
    private UserCredential credential;
    private Role role;
    private Status status;
    private Cart cart;
    private Session session;
}
