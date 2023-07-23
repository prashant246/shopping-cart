package com.shopping.cart.entity.repository;

import com.shopping.cart.entity.Product;

import java.util.List;

public interface ProductRepository {

    Product getById(String id);

    List<Product> getByIdIn(List<String> productIds);

    List<Product> getAllProduct();

    void save(Product product);
}
