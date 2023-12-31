package com.shopping.cart.utils;

import com.shopping.cart.datatypes.enums.Role;
import com.shopping.cart.datatypes.enums.SessionStatus;
import com.shopping.cart.datatypes.requests.LoginRequest;
import com.shopping.cart.domain.Session;
import com.shopping.cart.domain.User;
import com.shopping.cart.exception.CartServiceException;
import com.shopping.cart.exception.ErrorMessages;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class SessionHandler {

    @Value("${sessionTtlInMinutes}")
    private int sessionTtlInMinutes;

    /**
     * generates a new session with given TTL
     * @return
     */
    public Session create() {
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(sessionTtlInMinutes);
        String sessionId = UUID.randomUUID().toString();
        return Session.builder()
                .sessionId(sessionId)
                .expiryAt(expiryTime)
                .status(SessionStatus.LOGIN)
                .build();
    }

    /**
     * Validate the user session data if it is logged out or session is expired.
     * @param session
     */
    public void validate(Session session) {
        if (SessionStatus.LOGOUT.equals(session.getStatus())) {
            throw new CartServiceException(ErrorMessages.INVALID_SESSION_ID.getErrorMessage(), HttpStatus.UNAUTHORIZED);
        }
        if (session.getExpiryAt().isBefore(LocalDateTime.now())) {
            throw new CartServiceException(ErrorMessages.SESSION_EXPIRED.getErrorMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * validate the login reuqest data. Password is plain right now but this can
     * be stored in encrypted manner
     * @param request
     * @param user
     */
    public void validateLoginRequest(LoginRequest request, User user) {
        if (request.getPassword().equals(user.getCredential().getPassword())) {
            return;
        }
        throw new CartServiceException(ErrorMessages.INVALID_PASSWORD.getErrorMessage(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Validate if the current user is ADMIN or not
     * @param user
     */
    public void isAdmin(User user) {
        if (Role.ADMIN.equals(user.getRole())) {
            return;
        }
        throw new CartServiceException(ErrorMessages.AUTHORIZED_FOR_ADMIN.getErrorMessage(), HttpStatus.UNAUTHORIZED);
    }
}
