package com.shopping.cart.entity.repository;

import com.shopping.cart.entity.SessionDetail;

public interface SessionDetailRepository {

    SessionDetail getSessionForUserId(String userId);
    SessionDetail getUserIdForActiveSessionId(String sessionId);

    void save(SessionDetail sessionDetail);
}
