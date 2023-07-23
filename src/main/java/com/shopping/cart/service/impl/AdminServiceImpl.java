package com.shopping.cart.service.impl;

import com.shopping.cart.dao.UserEntityDetail;
import com.shopping.cart.datatypes.enums.Status;
import com.shopping.cart.datatypes.requests.SuspendUserRequest;
import com.shopping.cart.datatypes.responses.SuspendUserResponse;
import com.shopping.cart.domain.User;
import com.shopping.cart.exception.CartServiceException;
import com.shopping.cart.exception.ErrorMessages;
import com.shopping.cart.service.AdminService;
import com.shopping.cart.utils.SessionHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminServiceImpl implements AdminService {

    private final SessionHandler sessionHandler;
    private final UserEntityDetail userEntityDetail;

    @Autowired
    public AdminServiceImpl(SessionHandler sessionHandler, UserEntityDetail userEntityDetail) {
        this.sessionHandler = sessionHandler;
        this.userEntityDetail = userEntityDetail;
    }

    /**
     * suspend the user
     * @param suspendUserRequest
     * @param sessionId
     * @return
     */
    @Override
    public SuspendUserResponse suspendUser(SuspendUserRequest suspendUserRequest, String sessionId) {
        User user = userEntityDetail.createUserEntityForSessionId(sessionId);
        sessionHandler.validate(user.getSession());
        sessionHandler.isAdmin(user);
        List<String> success = new ArrayList<>();
        Map<String, String> failures = new HashMap<>();

        List<String> userIds = suspendUserRequest.getUserIds();
        userIds.stream().forEach(userId -> {
            try {
                User userEntityForUserId = userEntityDetail.createUserEntityForUserId(userId);
                userEntityForUserId.setStatus(Status.SUSPENDED);
                userEntityDetail.update(userEntityForUserId);
                success.add(userId);
            } catch (CartServiceException cse) {
                if (cse.getMessage().equals(ErrorMessages.INVALID_USER_ID.getErrorMessage())) {
                    failures.put(userId, ErrorMessages.INVALID_USER_ID.getErrorMessage());
                } else {
                    throw cse;
                }
            }
        });

        return SuspendUserResponse.builder()
                .success(success)
                .failures(failures)
                .build();
    }
}
