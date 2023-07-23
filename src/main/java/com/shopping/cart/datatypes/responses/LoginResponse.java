package com.shopping.cart.datatypes.responses;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LoginResponse {
    String userId;
    String sessionId;
    LocalDateTime expiryTime;
}
