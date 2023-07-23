package com.shopping.cart.service;

import com.shopping.cart.datatypes.requests.CreateOrUpdateUserRequest;
import com.shopping.cart.datatypes.requests.LoginRequest;
import com.shopping.cart.datatypes.responses.CreateUserResponse;
import com.shopping.cart.datatypes.responses.GetUserResponse;
import com.shopping.cart.datatypes.responses.LoginResponse;

public interface UserService {
    public CreateUserResponse create(CreateOrUpdateUserRequest createOrUpdateUserRequest);
    public void update(CreateOrUpdateUserRequest createOrUpdateUserRequest, String sessionId);
    public LoginResponse login(LoginRequest request);
    public void logout(String sessionId);
    GetUserResponse get(String sessionId);
}
