package com.shopping.cart.domain;

import com.shopping.cart.datatypes.enums.SessionStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * This contins the session details for authn/authz purpose
 */
@Data
@Builder
public class Session {
    private String sessionId;
    private LocalDateTime expiryAt;
    private SessionStatus status;
}
