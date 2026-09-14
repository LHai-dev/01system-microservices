package com.mptc.training.ecommerce.order.restapi.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record OrderAddressRequest(
        @NotNull
        @Size(max = 20)
        String street,
        @NotNull
        @Size(max = 10)
        String postCode,
        @NotNull
        @Size(max = 30)
        String city
) {
}
