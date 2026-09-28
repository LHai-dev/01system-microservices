package com.mptc.training.ecommerce.business.domain.dto;

import java.util.UUID;

public record OrderApprovalCommand(
    UUID businessId,
    UUID orderId,
    String approvalStatus
) {
}
