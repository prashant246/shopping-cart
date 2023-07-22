package com.shopping.cart.web;

import com.shopping.cart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * This will contain the rest endpoint for admin tasks.
 */

@RestController
@RequestMapping("/v1/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public void add() {
        productService.add();
    }

    @PutMapping
    public void update() {
        productService.update();
    }

    @GetMapping
    public void get() {
        productService.get();
    }
}
