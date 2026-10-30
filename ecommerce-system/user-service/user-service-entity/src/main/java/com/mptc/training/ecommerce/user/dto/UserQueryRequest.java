package com.mptc.training.ecommerce.user.dto;

import lombok.Builder;

@Builder
public record UserQueryRequest(
        String userName,
        String gender,
        String mobilePhone,
        String id,
        String role
) {
}
