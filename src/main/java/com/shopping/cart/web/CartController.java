package com.shopping.cart.web;

import com.shopping.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * this contains the rest api for cart related actions.
 */

@RestController
@RequestMapping("/v1/cart")
public class CartController {

    private final CartService cartService;

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping()
    public void get() {
        cartService.get();
    }

    @PostMapping(value = "/add")
    public void addProduct() {
        cartService.addProduct();
    }

    @PostMapping(value = "/remove")
    public void removeProduct() {
        cartService.removeProduct();
    }
}
