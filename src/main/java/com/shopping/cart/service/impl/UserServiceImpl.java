package com.shopping.cart.service.impl;

import com.shopping.cart.dao.UserEntityDetail;
import com.shopping.cart.datatypes.enums.SessionStatus;
import com.shopping.cart.datatypes.requests.CreateOrUpdateUserRequest;
import com.shopping.cart.datatypes.requests.LoginRequest;
import com.shopping.cart.datatypes.responses.CreateUserResponse;
import com.shopping.cart.datatypes.responses.GetUserResponse;
import com.shopping.cart.datatypes.responses.LoginResponse;
import com.shopping.cart.domain.Session;
import com.shopping.cart.domain.User;
import com.shopping.cart.domain.mappers.UserMapper;
import com.shopping.cart.service.UserService;
import com.shopping.cart.utils.SessionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final SessionHandler sessionHandler;
    private final UserEntityDetail userEntityDetail;

    @Autowired
    public UserServiceImpl(SessionHandler sessionHandler, UserEntityDetail userEntityDetail) {
        this.sessionHandler = sessionHandler;
        this.userEntityDetail = userEntityDetail;
    }

    /**
     * This function is to create/register new user based on role as customer/admin
     * @param createOrUpdateUserRequest - contains basic details of use to register
     * @return createUserResponse - returns userId with session details
     */
    @Override
    public CreateUserResponse create(CreateOrUpdateUserRequest createOrUpdateUserRequest) {
        // This will convert the request to user domain model
        User user = UserMapper.toUser(createOrUpdateUserRequest);

        // This will generate the session through which authn/authz will work
        Session session = sessionHandler.create();
        user.setSession(session);

        userEntityDetail.save(user);

        return UserMapper.toCreateUserResponse(user);
    }

    /**
     * This function update the user details using the sessionId
     * @param createOrUpdateUserRequest - updated value for user
     * @param sessionId - session detail of user
     */
    @Override
    public void update(CreateOrUpdateUserRequest createOrUpdateUserRequest, String sessionId) {
        User user = userEntityDetail.createUserEntityForSessionId(sessionId);
        sessionHandler.validate(user.getSession());
        updateUser(user, createOrUpdateUserRequest);
        userEntityDetail.update(user);
    }

    private void updateUser(User user, CreateOrUpdateUserRequest createOrUpdateUserRequest) {
        if (createOrUpdateUserRequest.getName() != null) {
            user.setName(createOrUpdateUserRequest.getName());
        }
        if(createOrUpdateUserRequest.getPassword() != null) {
            user.getCredential().setPassword(createOrUpdateUserRequest.getPassword());
        }
        if(createOrUpdateUserRequest.getRole() != null) {
            user.setRole(createOrUpdateUserRequest.getRole());
        }
    }

    /**
     * This function login the user with details and provide a session id that
     * can be used for further comm
     * @param request - contains the userId and password
     * @return loginResponse - contains the session detail of user
     */
    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userEntityDetail.createUserEntityForUserId(request.getUserId());
        sessionHandler.validateLoginRequest(request, user);
        Session session = sessionHandler.create();
        user.setSession(session);
        userEntityDetail.saveOrUpdateSession(user);
        return LoginResponse.builder()
                .userId(user.getCredential().getUserId())
                .sessionId(session.getSessionId())
                .expiryTime(session.getExpiryAt())
                .build();
    }

    /**
     * This function logout the user
     * @param sessionId - session id of the current user
     */
    @Override
    public void logout(String sessionId) {
        User user = userEntityDetail.createUserEntityForSessionId(sessionId);
        sessionHandler.validate(user.getSession());
        user.getSession().setStatus(SessionStatus.LOGOUT);
        userEntityDetail.saveOrUpdateSession(user);
    }

    /**
     * Get user details
     * @param sessionId
     * @return
     */
    @Override
    public GetUserResponse get(String sessionId) {
        User user = userEntityDetail.createUserEntityForSessionId(sessionId);
        sessionHandler.validate(user.getSession());
        return UserMapper.toGetUserResponse(user);
    }
}
