package com.mptc.training.ecommerce.customer.restapi.dto;

import java.util.UUID;

public record CustomerResponse(
        UUID id,
        String familyName,
        String givenName,
        String address
) {
}
