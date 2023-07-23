package com.shopping.cart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProductMapping extends BaseEntity{

    String userId;
    String productId;
    Type type;

    public UserProductMapping(String userId, String productId) {
        this.productId = productId;
        this.userId = userId;
        this.type = Type.CART;
    }

    public enum Type {
        CART,
        BOUGHT,
        REMOVED
    }
}
