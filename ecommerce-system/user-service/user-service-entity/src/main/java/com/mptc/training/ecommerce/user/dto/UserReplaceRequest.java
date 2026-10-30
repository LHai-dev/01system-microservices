package com.mptc.training.ecommerce.user.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.Date;

public record UserReplaceRequest(
        @NotBlank
        String username,
        @NotBlank
        String email,
        @NotBlank
        String familyName,
        @NotBlank
        String givenName,
        String gender,
        Date birthday,
        String mobilePhone,
        String address
) {
}
