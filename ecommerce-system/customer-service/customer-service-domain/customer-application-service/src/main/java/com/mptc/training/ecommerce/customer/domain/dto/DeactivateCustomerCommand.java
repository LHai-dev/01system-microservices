package com.mptc.training.ecommerce.customer.domain.dto;

import java.util.UUID;

public record DeactivateCustomerCommand(
        UUID customerId
) {
}
