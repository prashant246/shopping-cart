package com.shopping.cart.service;

import com.shopping.cart.datatypes.requests.SuspendUserRequest;
import com.shopping.cart.datatypes.responses.SuspendUserResponse;

public interface AdminService {
    public SuspendUserResponse suspendUser(SuspendUserRequest suspendUserRequest, String sessionId);
}
