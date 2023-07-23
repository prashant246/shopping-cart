package com.shopping.cart.datatypes.responses;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class SuspendUserResponse {
    List<String> success;
    Map<String, String> failures;
}
