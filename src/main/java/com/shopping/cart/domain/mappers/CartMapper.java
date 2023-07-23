package com.shopping.cart.domain.mappers;

import com.shopping.cart.datatypes.responses.GetCartResponse;
import com.shopping.cart.domain.Cart;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CartMapper {


    public static GetCartResponse toGetCartResponse(Cart cart) {
        List<GetCartResponse.CartProduct> collect = new ArrayList<>();
        cart.getProducts().stream().forEach(product -> {
            GetCartResponse.CartProduct cartProduct = new GetCartResponse.CartProduct();
            cartProduct.setId(product.getProductId());
            cartProduct.setName(product.getTitle());
            cartProduct.setPrice(product.getPrice());
            cartProduct.setDiscount(product.getDiscount());
            collect.add(cartProduct);
        });
        return GetCartResponse.builder()
                .cartDiscount(cart.getTotalDiscount())
                .cartValue(cart.getTotalAmount())
                .cartProductList(collect)
                .build();
    }
}
