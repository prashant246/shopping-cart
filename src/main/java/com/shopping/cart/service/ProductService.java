package com.shopping.cart.service;

import com.shopping.cart.datatypes.requests.AddOrUpdateProductRequest;
import com.shopping.cart.datatypes.responses.AddOrUpdateProductResponse;
import com.shopping.cart.datatypes.responses.GetProductResponse;

public interface ProductService {

    public AddOrUpdateProductResponse add(AddOrUpdateProductRequest addOrUpdateProductRequest, String sessionId);
    public AddOrUpdateProductResponse update(AddOrUpdateProductRequest addOrUpdateProductRequest, String sessionId);
    public GetProductResponse get(String sessionId);
}
