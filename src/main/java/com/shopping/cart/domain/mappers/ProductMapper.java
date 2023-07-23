package com.shopping.cart.domain.mappers;

import com.shopping.cart.datatypes.enums.ProductStatus;
import com.shopping.cart.datatypes.requests.AddOrUpdateProductRequest;
import com.shopping.cart.datatypes.responses.AddOrUpdateProductResponse;
import com.shopping.cart.domain.Product;

import java.util.UUID;

public class ProductMapper {

    public static Product toProduct(AddOrUpdateProductRequest request) {
        return Product.builder()
                .productId(UUID.randomUUID().toString())
                .title(request.getName())
                .price(request.getPrice())
                .count(request.getCount())
                .status(ProductStatus.AVAILABLE)
                .discount(request.getDiscount())
                .build();
    }

    public static Product toExistingProduct(AddOrUpdateProductRequest request) {
        return Product.builder()
                .title(request.getName())
                .price(request.getPrice())
                .count(request.getCount())
                .discount(request.getDiscount())
                .build();
    }

    public static AddOrUpdateProductResponse toAddOrUpdateProductResponse(Product product) {
        return AddOrUpdateProductResponse.builder()
                .productId(product.getProductId())
                .name(product.getTitle())
                .build();
    }
}
