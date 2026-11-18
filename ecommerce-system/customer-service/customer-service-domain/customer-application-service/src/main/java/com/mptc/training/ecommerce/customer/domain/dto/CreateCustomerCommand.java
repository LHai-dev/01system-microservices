package com.mptc.training.ecommerce.customer.domain.dto;

import lombok.Builder;

@Builder
public record CreateCustomerCommand(
        String userId,
        String username,
        String familyName,
        String givenName,
        String email,
        String phoneNumber
) {
}
