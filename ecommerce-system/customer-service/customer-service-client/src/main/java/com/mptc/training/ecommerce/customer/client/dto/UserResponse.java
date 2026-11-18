package com.mptc.training.ecommerce.customer.client.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public record UserResponse(
        String id,
        String giveName,
        String familyName,
        String gender,
        String givenName,
        String mobilePhone,
        String address,
        Date birthday
) {
}
