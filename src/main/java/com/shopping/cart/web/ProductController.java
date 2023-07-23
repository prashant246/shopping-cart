package com.shopping.cart.web;

import com.shopping.cart.datatypes.requests.AddOrUpdateProductRequest;
import com.shopping.cart.datatypes.requests.CreateOrUpdateUserRequest;
import com.shopping.cart.datatypes.responses.AddOrUpdateProductResponse;
import com.shopping.cart.datatypes.responses.GetProductResponse;
import com.shopping.cart.exception.CartServiceException;
import com.shopping.cart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.shopping.cart.datatypes.Constants.X_SESSION_ID;

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
    public ResponseEntity add(@RequestHeader(value = X_SESSION_ID, required = true) String sessionId,
                    @RequestBody AddOrUpdateProductRequest addOrUpdateProductRequest) {
        try {
            AddOrUpdateProductResponse response = productService.add(addOrUpdateProductRequest, sessionId);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (CartServiceException cse) {
            return new ResponseEntity(cse.getMessage(), cse.getHttpStatus());
        }
    }

    @PutMapping
    public ResponseEntity update(@RequestHeader(value = X_SESSION_ID, required = true) String sessionId,
                       @RequestBody AddOrUpdateProductRequest addOrUpdateProductRequest) {
        try {
            AddOrUpdateProductResponse response = productService.update(addOrUpdateProductRequest, sessionId);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (CartServiceException cse) {
            return new ResponseEntity(cse.getMessage(), cse.getHttpStatus());
        }
    }

    @GetMapping
    public ResponseEntity get(@RequestHeader(value = X_SESSION_ID, required = true) String sessionId) {
        try {
            GetProductResponse response = productService.get(sessionId);
            return new ResponseEntity(response, HttpStatus.OK);
        } catch (CartServiceException cse) {
            return new ResponseEntity(cse.getMessage(), cse.getHttpStatus());
        }
    }
}
