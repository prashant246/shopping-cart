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

    public enum Type {
        CART,
        BOUGHT,
        REMOVED
    }
}
