package com.shopping.cart.dao;

import com.shopping.cart.datatypes.enums.Role;
import com.shopping.cart.datatypes.enums.SessionStatus;
import com.shopping.cart.datatypes.enums.Status;
import com.shopping.cart.domain.Cart;
import com.shopping.cart.domain.Session;
import com.shopping.cart.domain.UserCredential;
import com.shopping.cart.entity.Product;
import com.shopping.cart.entity.SessionDetail;
import com.shopping.cart.entity.User;
import com.shopping.cart.entity.UserProductMapping;
import com.shopping.cart.entity.repository.ProductRepository;
import com.shopping.cart.entity.repository.SessionDetailRepository;
import com.shopping.cart.entity.repository.UserProductMappingRepository;
import com.shopping.cart.entity.repository.UserRepository;
import com.shopping.cart.exception.CartServiceException;
import com.shopping.cart.exception.ErrorMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class UserEntityDetail {

    private final ProductRepository productRepository;
    private final SessionDetailRepository sessionDetailRepository;
    private final UserProductMappingRepository userProductMappingRepository;
    private final UserRepository userRepository;

    @Autowired
    public UserEntityDetail(ProductRepository productRepository, SessionDetailRepository sessionDetailRepository, UserProductMappingRepository userProductMappingRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.sessionDetailRepository = sessionDetailRepository;
        this.userProductMappingRepository = userProductMappingRepository;
        this.userRepository = userRepository;
    }

    public com.shopping.cart.domain.User createUserEntityForSessionId(String sessionId) {
        SessionDetail sessionDetail = sessionDetailRepository.getUserIdForActiveSessionId(sessionId);
        if (sessionDetail == null) {
            throw new CartServiceException(ErrorMessages.INVALID_SESSION_ID.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        User user = userRepository.getUserById(sessionDetail.getUserId());
        return getUser(sessionDetail, user);
    }

    private com.shopping.cart.domain.User getUser(SessionDetail sessionDetail, User user) {
        List<UserProductMapping> productForUserId = userProductMappingRepository.getProductForUserId(sessionDetail.getUserId());
        List<Product> products = productRepository.getByIdIn(productForUserId.stream().map(productUser -> productUser.getProductId()).collect(Collectors.toList()));

        return com.shopping.cart.domain.User.builder()
                .session(Session.builder()
                        .status(SessionStatus.valueOf(sessionDetail.getStatus()))
                        .expiryAt(sessionDetail.getExpiryTime())
                        .sessionId(sessionDetail.getSessionId())
                        .build())
                .name(user.getName())
                .role(Role.valueOf(user.getRole()))
                .credential(UserCredential.builder()
                        .password(user.getPassword())
                        .userId(user.getId())
                        .build())
                .status(Status.valueOf(user.getStatus()))
                .cart(getCartDetails(productForUserId, products))
                .build();
    }

    public com.shopping.cart.domain.User createUserEntityForUserId(String userId) {
        User user = userRepository.getUserById(userId);
        if (user == null) {
            throw new CartServiceException(ErrorMessages.INVALID_USER_ID.getErrorMessage(), HttpStatus.BAD_REQUEST);
        }
        SessionDetail sessionDetail = sessionDetailRepository.getSessionForUserId(userId);
        return getUser(sessionDetail, user);
    }

    private Cart getCartDetails(List<UserProductMapping> productForUserId, List<Product> products) {
        List<UserProductMapping> cartProduct = productForUserId.stream()
                .filter(userProductMapping -> UserProductMapping.Type.CART.equals(userProductMapping.getType()))
                .collect(Collectors.toList());
        Map<String, Product> productToIdMap = products.stream().collect(Collectors.toMap(Product::getId, Function.identity()));

        Double totalAmount = 0.0;
        Double totalDiscount = 0.0;

        List<com.shopping.cart.domain.Product> productsList = new ArrayList<>();

        for(UserProductMapping userProductMapping : cartProduct) {
            Product product = productToIdMap.get(userProductMapping.getProductId());
            totalAmount += product.getPrice();
            totalDiscount += product.getDiscount();
            productsList.add(com.shopping.cart.domain.Product.builder()
                            .title(product.getName())
                            .price(product.getPrice())
                            .productId(product.getId())
                    .build());
        }

        return Cart.builder()
                .totalAmount(totalAmount)
                .products(productsList)
                .totalDiscount(totalDiscount)
                .build();

    }

    public void save(com.shopping.cart.domain.User user) {
        userRepository.save(new User(user));
        sessionDetailRepository.save(new SessionDetail(user));
    }

    public void saveOrUpdateSession(com.shopping.cart.domain.User user) {
        SessionDetail userIdForActiveSessionId = sessionDetailRepository.getSessionForUserId(user.getCredential().getUserId());
        if (userIdForActiveSessionId == null) {
            SessionDetail sessionDetail = new SessionDetail(user);
            sessionDetailRepository.save(sessionDetail);
        }
        userIdForActiveSessionId.setStatus(user.getSession().getStatus().name());
        userIdForActiveSessionId.setSessionId(user.getSession().getSessionId());
        userIdForActiveSessionId.setExpiryTime(user.getSession().getExpiryAt());
    }

    public void update(com.shopping.cart.domain.User user) {
        User userById = userRepository.getUserById(user.getCredential().getUserId());
        userById.setName(user.getName());
        userById.setRole(user.getRole().name());
        userById.setPassword(user.getCredential().getPassword());

    }
}
