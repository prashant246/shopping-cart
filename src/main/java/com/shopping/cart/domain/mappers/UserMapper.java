package com.shopping.cart.domain.mappers;

import com.shopping.cart.datatypes.enums.Status;
import com.shopping.cart.datatypes.requests.CreateOrUpdateUserRequest;
import com.shopping.cart.datatypes.responses.CreateUserResponse;
import com.shopping.cart.datatypes.responses.GetUserResponse;
import com.shopping.cart.datatypes.responses.LoginResponse;
import com.shopping.cart.domain.User;
import com.shopping.cart.domain.UserCredential;

public class UserMapper {

    public static User toUser(CreateOrUpdateUserRequest request) {
        return User.builder()
                .role(request.getRole())
                .name(request.getName())
                .status(Status.ACTIVE)
                .credential(UserCredential.builder()
                        .userId(request.getUserId())
                        .password(request.getPassword())
                        .build())
                .build();
    }

    public static CreateUserResponse toCreateUserResponse(User user) {
        return CreateUserResponse.builder()
                .userId(user.getCredential().getUserId())
                .loginDetail(LoginResponse.builder()
                        .sessionId(user.getSession().getSessionId())
                        .expiryTime(user.getSession().getExpiryAt())
                        .userId(user.getCredential().getUserId())
                        .build())
                .build();
    }

    public static GetUserResponse toGetUserResponse(User user) {
        return GetUserResponse.builder()
                .name(user.getName())
                .status(user.getStatus())
                .role(user.getRole())
                .build();
    }
}
