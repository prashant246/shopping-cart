package com.shopping.cart.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User extends BaseEntity {
    private String name;
    private String role;
    private String password;
    private String status;

    public User(com.shopping.cart.domain.User user) {
        this.name = user.getName();
        this.role = user.getRole().name();
        this.password = user.getCredential().getPassword();
        this.status = user.getStatus().toString();
        this.setId(user.getCredential().getUserId());
    }
}
