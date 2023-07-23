package com.shopping.cart.service.impl;

import com.shopping.cart.dao.ProductEntityDetail;
import com.shopping.cart.dao.UserEntityDetail;
import com.shopping.cart.datatypes.requests.AddOrUpdateProductRequest;
import com.shopping.cart.datatypes.responses.AddOrUpdateProductResponse;
import com.shopping.cart.datatypes.responses.GetProductResponse;
import com.shopping.cart.domain.Product;
import com.shopping.cart.domain.User;
import com.shopping.cart.domain.mappers.ProductMapper;
import com.shopping.cart.exception.CartServiceException;
import com.shopping.cart.exception.ErrorMessages;
import com.shopping.cart.service.ProductService;
import com.shopping.cart.utils.SessionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final SessionHandler sessionHandler;
    private final UserEntityDetail userEntityDetail;
    private final ProductEntityDetail productEntityDetail;

    @Autowired
    public ProductServiceImpl(SessionHandler sessionHandler, UserEntityDetail userEntityDetail, ProductEntityDetail productEntityDetail) {
        this.sessionHandler = sessionHandler;
        this.userEntityDetail = userEntityDetail;
        this.productEntityDetail = productEntityDetail;
    }

    @Override
    public AddOrUpdateProductResponse add(AddOrUpdateProductRequest addOrUpdateProductRequest, String sessionId) {
        User user = userEntityDetail.createUserEntityForSessionId(sessionId);
        sessionHandler.validate(user.getSession());
        sessionHandler.isAdmin(user);
        Product product = ProductMapper.toProduct(addOrUpdateProductRequest);
        productEntityDetail.addProduct(product);
        return ProductMapper.toAddOrUpdateProductResponse(product);
    }

    @Override
    public AddOrUpdateProductResponse update(AddOrUpdateProductRequest addOrUpdateProductRequest, String sessionId) {
        User user = userEntityDetail.createUserEntityForSessionId(sessionId);
        sessionHandler.validate(user.getSession());
        sessionHandler.isAdmin(user);
        if (addOrUpdateProductRequest.getId() == null) {
            throw new CartServiceException(ErrorMessages.PRODUCT_ID_NOT_PRESENT.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        Product existingProduct = ProductMapper.toExistingProduct(addOrUpdateProductRequest);
        String productId = productEntityDetail.updateProduct(existingProduct);
        AddOrUpdateProductResponse addOrUpdateProductResponse = ProductMapper.toAddOrUpdateProductResponse(existingProduct);
        addOrUpdateProductResponse.setProductId(productId);
        return addOrUpdateProductResponse;
    }

    @Override
    public GetProductResponse get(String sessionId) {
        User user = userEntityDetail.createUserEntityForSessionId(sessionId);
        sessionHandler.validate(user.getSession());
        List<Product> products = productEntityDetail.getAvailableProducts();
        return GetProductResponse.builder()
                .productList(products)
                .build();
    }
}
