package com.shopping.cart.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product extends BaseEntity{
    String name;
    Double price;
    Double discount;
    Integer count;

    public Product(com.shopping.cart.domain.Product product) {
        this.name = product.getTitle();
        this.price = product.getPrice();
        this.discount = product.getDiscount();
        this.count = product.getCount();
        this.setId(product.getProductId());
    }
}
