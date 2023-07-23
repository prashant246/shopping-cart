package com.shopping.cart.entity.repository.impl;

import com.shopping.cart.datatypes.enums.SessionStatus;
import com.shopping.cart.entity.SessionDetail;
import com.shopping.cart.entity.repository.SessionDetailRepository;
import com.shopping.cart.entity.repository.datasource.InMemoryDataSource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class InMemorySessionDetailsRepositoryImpl implements SessionDetailRepository {
    @Override
    public SessionDetail getSessionForUserId(String userId) {
        List<SessionDetail> sessionDetails = InMemoryDataSource.getSessionDetails();
        Optional<SessionDetail> first = sessionDetails.stream().filter(session -> userId.equals(session.getUserId())).findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        return null;
    }

    @Override
    public SessionDetail getUserIdForActiveSessionId(String sessionId) {
        List<SessionDetail> sessionDetails = InMemoryDataSource.getSessionDetails();
        Optional<SessionDetail> first = sessionDetails.stream()
                .filter(session -> sessionId.equals(session.getSessionId()))
                .filter(session -> SessionStatus.LOGIN.name().equals(session.getStatus()))
                .findFirst();
        if (first.isPresent()) {
            return first.get();
        }
        return null;
    }

    @Override
    public void save(SessionDetail sessionDetail) {
        InMemoryDataSource.addSessionDetail(sessionDetail);
    }
}
