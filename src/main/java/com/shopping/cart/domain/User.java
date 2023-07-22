package com.shopping.cart.domain;

import com.shopping.cart.datatypes.enums.Role;
import com.shopping.cart.datatypes.enums.Status;
import lombok.Data;

import java.util.List;

/**
 * User domain is the business entity with minimal details.
 * It will contain basic details of user.
 */

@Data
public class User {

    private String name;
    private String userId;
    private Role role;
    private Status status;
    private List<Product> purchasedProducts;
    private Cart cart;
}
