package com.shopping.cart.service;

import com.shopping.cart.datatypes.requests.AddOrRemoveProduct;
import com.shopping.cart.datatypes.responses.GetCartResponse;

public interface CartService {
    public GetCartResponse get(String sessionId);
    public void removeProduct(AddOrRemoveProduct addOrRemoveProduct, String sessionId);
    public void addProduct(AddOrRemoveProduct addOrRemoveProduct, String sessionId);

}
