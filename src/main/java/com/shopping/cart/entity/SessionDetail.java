package com.shopping.cart.entity;

import com.shopping.cart.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SessionDetail extends BaseEntity{

    String sessionId;
    String userId;
    String status;
    LocalDateTime expiryTime;

    public SessionDetail(User user) {
        this.sessionId = user.getSession().getSessionId();
        this.userId = user.getCredential().getUserId();
        this.status = user.getSession().getStatus().name();
        this.expiryTime = user.getSession().getExpiryAt();
    }
}
