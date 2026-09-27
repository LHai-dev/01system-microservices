package com.mptc.training.ecommerce.business.rest;

import com.mptc.training.ecommerce.business.domain.valueobject.OrderApprovalStatus;

import java.util.List;
import java.util.UUID;

public record OrderApprovalResponse(
        UUID id,
        UUID businessId,
        UUID orderId,
        OrderApprovalStatus status,
        List<String> failureMessages
) {
}
