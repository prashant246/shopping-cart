package com.shopping.cart.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Cart is the business entity for shopping cart.
 * It will contain the products, amount etc.
 */

@Data
@Builder
public class Cart {
    private List<Product> products;
    private Double totalAmount;
    private Double totalDiscount;
}
