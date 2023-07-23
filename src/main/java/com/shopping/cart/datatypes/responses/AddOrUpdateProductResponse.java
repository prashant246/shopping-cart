package com.shopping.cart.datatypes.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddOrUpdateProductResponse {

    private String name;
    private String productId;
}
