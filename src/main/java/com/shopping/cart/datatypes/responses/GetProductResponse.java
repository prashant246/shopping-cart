package com.shopping.cart.datatypes.responses;

import com.shopping.cart.domain.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class GetProductResponse {
    List<Product> productList;
}
