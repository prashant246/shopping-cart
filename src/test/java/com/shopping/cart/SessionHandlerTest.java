package com.shopping.cart;

import com.shopping.cart.datatypes.enums.SessionStatus;
import com.shopping.cart.datatypes.requests.LoginRequest;
import com.shopping.cart.domain.Session;
import com.shopping.cart.domain.User;
import com.shopping.cart.domain.UserCredential;
import com.shopping.cart.exception.CartServiceException;
import com.shopping.cart.exception.ErrorMessages;
import com.shopping.cart.utils.SessionHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class SessionHandlerTest {

    @InjectMocks
    SessionHandler sessionHandler;

    @Test
    public void createTest() {
        Session session = sessionHandler.create();
        Assertions.assertEquals(session.getStatus(), SessionStatus.LOGIN);
        Assertions.assertNotNull(session.getSessionId());
    }

    @Test
    public void validateTest_1() {
        Session session = Session.builder()
                .status(SessionStatus.LOGOUT)
                .build();
        try {
            sessionHandler.validate(session);
        } catch (CartServiceException cartServiceException) {
            Assertions.assertEquals(cartServiceException.getHttpStatus(), HttpStatus.UNAUTHORIZED);
            Assertions.assertEquals(cartServiceException.getMessage(), ErrorMessages.INVALID_SESSION_ID.getErrorMessage());
        }

    }

    @Test
    public void validateTest_2() {
        Session session = Session.builder()
                .status(SessionStatus.LOGIN)
                .expiryAt(LocalDateTime.now().minusMinutes(3))
                .build();
        try {
            sessionHandler.validate(session);
        } catch (CartServiceException cartServiceException) {
            Assertions.assertEquals(cartServiceException.getHttpStatus(), HttpStatus.UNAUTHORIZED);
            Assertions.assertEquals(cartServiceException.getMessage(), ErrorMessages.SESSION_EXPIRED.getErrorMessage());
        }
    }

    @Test
    public void validateTest_3() {
        Session session = Session.builder()
                .status(SessionStatus.LOGIN)
                .expiryAt(LocalDateTime.now().plusMinutes(3))
                .build();
        sessionHandler.validate(session);

    }

    @Test
    public void validateLoginRequestTest_1() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("pass");
        loginRequest.setUserId("user");

        User user = User.builder().credential(UserCredential.builder().password("pass").build()).build();
        sessionHandler.validateLoginRequest(loginRequest, user);
    }

    @Test
    public void validateLoginRequestTest_2() {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setPassword("pass1");
        loginRequest.setUserId("user");

        User user = User.builder().credential(UserCredential.builder().password("pass").build()).build();
        try {
            sessionHandler.validateLoginRequest(loginRequest, user);
        } catch (CartServiceException cartServiceException) {
            Assertions.assertEquals(cartServiceException.getHttpStatus(), HttpStatus.BAD_REQUEST);
            Assertions.assertEquals(cartServiceException.getMessage(), ErrorMessages.INVALID_PASSWORD.getErrorMessage());
        }
    }
}
