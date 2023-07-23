package com.shopping.cart.dao;

import com.shopping.cart.datatypes.enums.ProductStatus;
import com.shopping.cart.entity.Product;
import com.shopping.cart.entity.repository.ProductRepository;
import com.shopping.cart.exception.CartServiceException;
import com.shopping.cart.exception.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductEntityDetail {

    private final ProductRepository productRepository;

    @Autowired
    public ProductEntityDetail(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void addProduct(com.shopping.cart.domain.Product product) {
        productRepository.save(new Product(product));
    }

    public String updateProduct(com.shopping.cart.domain.Product product) {
        Product byId = productRepository.getById(product.getProductId());
        if (byId == null) {
            throw new CartServiceException(ErrorMessages.PRODUCT_ID_NOT_PRESENT.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        byId.setId(product.getProductId());
        byId.setName(product.getTitle());
        byId.setCount(product.getCount());
        byId.setPrice(product.getPrice());
        byId.setDiscount(product.getDiscount());

        return byId.getId();
    }

    public com.shopping.cart.domain.Product getProductById(String productId) {
        Product product = productRepository.getById(productId);
        com.shopping.cart.domain.Product productToAdd = com.shopping.cart.domain.Product.builder()
                .productId(product.getId())
                .count(product.getCount())
                .price(product.getPrice())
                .title(product.getName())
                .discount(product.getDiscount())
                .status(ProductStatus.AVAILABLE)
                .build();
        if (product.getCount() < 1) {
            productToAdd.setStatus(ProductStatus.UNAVAILABLE);
        }
        return productToAdd;
    }

    public List<com.shopping.cart.domain.Product> getAvailableProducts() {
        List<Product> allProduct = productRepository.getAllProduct();
        List<com.shopping.cart.domain.Product> products = allProduct.stream().filter(product -> product.getCount() > 0).map(
                product -> com.shopping.cart.domain.Product.builder()
                        .productId(product.getId())
                        .count(product.getCount())
                        .price(product.getPrice())
                        .title(product.getName())
                        .discount(product.getDiscount())
                        .status(ProductStatus.AVAILABLE)
                        .build()
        ).collect(Collectors.toList());
        return products;
    }
}
