package com.shopping.cart.domain;

import lombok.Data;

import java.util.List;

/**
 * Cart is the business entity for shopping cart.
 * It will contain the products, amount etc.
 */

@Data
public class Cart {
    private List<Product> products;
    private Double totalAmount;
    private Double totalDiscount;
}
