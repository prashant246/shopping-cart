package com.shopping.cart.datatypes.requests;

import lombok.Data;

@Data
public class AddOrUpdateProductRequest {
    private String name;
    private Integer count;
    private Double price;
    private Double discount;
    private String id;
}
