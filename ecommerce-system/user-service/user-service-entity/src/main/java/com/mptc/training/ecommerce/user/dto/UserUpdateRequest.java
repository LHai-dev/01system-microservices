package com.mptc.training.ecommerce.user.dto;

import lombok.Builder;

import java.util.Date;

@Builder
public record UserUpdateRequest(
        String familyName,
        String giveName,
        String gender,
        Date birthday,
        String mobilePhone

) {
}
