package com.shopping.cart.domain;

import com.shopping.cart.datatypes.enums.ProductStatus;
import lombok.Data;

/**
 * Product domain deal with basic structure of products
 * This is the business entity for product
 */

@Data
public class Product {

    private String title;
    private String productId;
    private Double price;
    private Double discount;
    private Integer count;
    private ProductStatus status;
}
