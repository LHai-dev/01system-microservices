package com.mptc.training.ecommerce.business.rest;

import java.util.List;
import java.util.UUID;

public record OrderPaidApprovalRequest(
        UUID orderId,
        List<ProductQuantity> products
) {
    public record ProductQuantity(UUID productId, int quantity) {
    }
}
