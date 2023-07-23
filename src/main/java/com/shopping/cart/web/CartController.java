package com.shopping.cart.web;

import com.shopping.cart.datatypes.requests.AddOrRemoveProduct;
import com.shopping.cart.datatypes.responses.GetCartResponse;
import com.shopping.cart.exception.CartServiceException;
import com.shopping.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.shopping.cart.datatypes.Constants.X_SESSION_ID;

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
    public ResponseEntity get(@RequestHeader(value = X_SESSION_ID, required = true) String sessionId) {
        try {
            GetCartResponse response = cartService.get(sessionId);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (CartServiceException cse) {
            return new ResponseEntity(cse.getMessage(), cse.getHttpStatus());
        }
    }

    @PostMapping(value = "/add")
    public ResponseEntity addProduct(@RequestHeader(value = X_SESSION_ID, required = true) String sessionId,
                                     @RequestBody AddOrRemoveProduct addOrRemoveProduct) {
        try {
            cartService.addProduct(addOrRemoveProduct, sessionId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (CartServiceException cse) {
            return new ResponseEntity(cse.getMessage(), cse.getHttpStatus());
        }
    }

    @PostMapping(value = "/remove")
    public ResponseEntity removeProduct(@RequestHeader(value = X_SESSION_ID, required = true) String sessionId,
                                        @RequestBody AddOrRemoveProduct addOrRemoveProduct) {
        try {
            cartService.removeProduct(addOrRemoveProduct, sessionId);
            return new ResponseEntity(HttpStatus.OK);
        } catch (CartServiceException cse) {
            return new ResponseEntity(cse.getMessage(), cse.getHttpStatus());
        }
    }
}
