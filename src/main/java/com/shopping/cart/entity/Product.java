package com.shopping.cart.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
}
