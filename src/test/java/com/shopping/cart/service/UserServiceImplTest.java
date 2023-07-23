package com.shopping.cart.service;

import com.shopping.cart.dao.UserEntityDetail;
import com.shopping.cart.datatypes.enums.Role;
import com.shopping.cart.datatypes.enums.SessionStatus;
import com.shopping.cart.datatypes.requests.CreateOrUpdateUserRequest;
import com.shopping.cart.datatypes.requests.LoginRequest;
import com.shopping.cart.datatypes.responses.CreateUserResponse;
import com.shopping.cart.datatypes.responses.LoginResponse;
import com.shopping.cart.domain.Session;
import com.shopping.cart.domain.User;
import com.shopping.cart.domain.UserCredential;
import com.shopping.cart.service.impl.UserServiceImpl;
import com.shopping.cart.utils.SessionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {


    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private SessionHandler sessionHandler;

    @Mock
    private UserEntityDetail userEntityDetail;

    @Test
    public void createTest() {
        CreateOrUpdateUserRequest createOrUpdateUserRequest = new CreateOrUpdateUserRequest();
        createOrUpdateUserRequest.setUserId("testId");
        createOrUpdateUserRequest.setRole(Role.CUSTOMER);
        createOrUpdateUserRequest.setName("test");
        createOrUpdateUserRequest.setPassword("password");

        when(sessionHandler.create()).thenReturn(Session.builder()
                        .sessionId("session-id")
                        .status(SessionStatus.LOGIN)
                        .expiryAt(LocalDateTime.now().plusMinutes(20))
                .build());

        CreateUserResponse createUserResponse = userService.create(createOrUpdateUserRequest);
        Assertions.assertEquals(createUserResponse.getUserId(), "testId");
        Assertions.assertEquals(createUserResponse.getLoginDetail().getSessionId(), "session-id");
    }

    @Test
    public void updateTest() {
        CreateOrUpdateUserRequest createOrUpdateUserRequest = new CreateOrUpdateUserRequest();
        createOrUpdateUserRequest.setName("test");
        String sessionId = "session-id";

        when(userEntityDetail.createUserEntityForSessionId(sessionId)).thenReturn(User.builder()
                        .session(Session.builder().sessionId(sessionId).build())
                .build());
        userService.update(createOrUpdateUserRequest, sessionId);
    }

    @Test
    public void loginTest() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserId("user");
        loginRequest.setPassword("password");

        when(userEntityDetail.createUserEntityForUserId("user")).thenReturn(User.builder()
                .credential(UserCredential.builder()
                        .password("password")
                        .build()).build());
        when(sessionHandler.create()).thenReturn(Session.builder()
                .sessionId("session-id")
                .status(SessionStatus.LOGIN)
                .expiryAt(LocalDateTime.now().plusMinutes(20))
                .build());

        LoginResponse login = userService.login(loginRequest);
        Assertions.assertEquals(login.getSessionId(), "session-id");
    }
}
