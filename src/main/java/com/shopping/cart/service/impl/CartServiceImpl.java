package com.shopping.cart.service.impl;

import com.shopping.cart.dao.ProductEntityDetail;
import com.shopping.cart.dao.UserEntityDetail;
import com.shopping.cart.datatypes.enums.ProductStatus;
import com.shopping.cart.datatypes.requests.AddOrRemoveProduct;
import com.shopping.cart.datatypes.responses.GetCartResponse;
import com.shopping.cart.domain.Cart;
import com.shopping.cart.domain.Product;
import com.shopping.cart.domain.User;
import com.shopping.cart.domain.mappers.CartMapper;
import com.shopping.cart.domain.mappers.UserMapper;
import com.shopping.cart.exception.CartServiceException;
import com.shopping.cart.exception.ErrorMessages;
import com.shopping.cart.service.CartService;
import com.shopping.cart.utils.SessionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    private final SessionHandler sessionHandler;
    private final UserEntityDetail userEntityDetail;
    private final ProductEntityDetail productEntityDetail;

    public CartServiceImpl(SessionHandler sessionHandler, UserEntityDetail userEntityDetail, ProductEntityDetail productEntityDetail) {
        this.sessionHandler = sessionHandler;
        this.userEntityDetail = userEntityDetail;
        this.productEntityDetail = productEntityDetail;
    }

    /**
     * get the cart details of the user
     * @param sessionId
     * @return
     */
    @Override
    public GetCartResponse get(String sessionId) {
        User user = userEntityDetail.createUserEntityForSessionId(sessionId);
        sessionHandler.validate(user.getSession());
        Cart cart = user.getCart();
        return CartMapper.toGetCartResponse(cart);
    }

    /**
     * remove the product from cart
     * @param addOrRemoveProduct
     * @param sessionId
     */
    @Override
    public void removeProduct(AddOrRemoveProduct addOrRemoveProduct, String sessionId) {
        User user = userEntityDetail.createUserEntityForSessionId(sessionId);
        sessionHandler.validate(user.getSession());
        Cart cart = user.getCart();
        Optional<Product> productToRemove = cart.getProducts().stream().filter(
                prod -> addOrRemoveProduct.getProductId().equals(prod.getProductId())).findFirst();
        if (!productToRemove.isPresent()) {
            throw new CartServiceException(ErrorMessages.PRODUCT_ID_NOT_PRESENT.getErrorMessage(), HttpStatus.NOT_FOUND);
        }
        Product product = productToRemove.get();
        cart.getProducts().remove(product);
        userEntityDetail.removeFromCart(user.getCredential().getUserId(), product.getProductId());
        Product productById = productEntityDetail.getProductById(addOrRemoveProduct.getProductId());
        productById.setCount(productById.getCount() + 1);
        productEntityDetail.updateProduct(productById);
    }

    /**
     * add product to cart
     * @param addOrRemoveProduct
     * @param sessionId
     */
    @Override
    public void addProduct(AddOrRemoveProduct addOrRemoveProduct, String sessionId) {
        User user = userEntityDetail.createUserEntityForSessionId(sessionId);
        sessionHandler.validate(user.getSession());
        Product productById = productEntityDetail.getProductById(addOrRemoveProduct.getProductId());
        if (ProductStatus.UNAVAILABLE.equals(productById.getStatus())) {
            throw new CartServiceException(ErrorMessages.PRODUCT_NOT_AVAILABLE.getErrorMessage(), HttpStatus.NOT_FOUND);
        }
        userEntityDetail.addToCart(user.getCredential().getUserId(), productById.getProductId());
        productById.setCount(productById.getCount() - 1);
        productEntityDetail.updateProduct(productById);
    }
}
