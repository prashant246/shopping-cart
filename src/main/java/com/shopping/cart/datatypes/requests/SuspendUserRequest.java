package com.shopping.cart.datatypes.requests;

import lombok.Data;

import java.util.List;

@Data
public class SuspendUserRequest {
    List<String> userIds;
}
