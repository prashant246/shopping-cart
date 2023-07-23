package com.shopping.cart.entity.repository.impl;

import com.shopping.cart.entity.Product;
import com.shopping.cart.entity.repository.ProductRepository;
import com.shopping.cart.entity.repository.datasource.InMemoryDataSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class InMemoryProductRepositoryImpl implements ProductRepository {
    @Override
    public Product getById(String id) {
        List<Product> products = InMemoryDataSource.getProducts();
        Optional<Product> first = products.stream().filter(product -> id.equals(product.getId())).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        return null;
    }

    @Override
    public List<Product> getByIdIn(List<String> productIds) {
        List<Product> products = InMemoryDataSource.getProducts();
        return products.stream().filter(product -> productIds.contains(product.getId())).collect(Collectors.toList());
    }

    @Override
    public List<Product> getAllProduct() {
        return InMemoryDataSource.getProducts();
    }

    @Override
    public void save(Product product) {
        InMemoryDataSource.addProduct(product);
    }
}
