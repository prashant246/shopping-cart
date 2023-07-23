package com.shopping.cart.datatypes.responses;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
public class GetCartResponse {
    private List<CartProduct> cartProductList;
    private Double cartValue;
    private Double cartDiscount;

    @Data
    @NoArgsConstructor
    public static class CartProduct {
        String name;
        String id;
        Double price;
        Double discount;
    }
}
