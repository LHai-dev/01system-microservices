package com.mptc.training.ecommerce.business.domain.event;


import com.mptc.training.ecommerce.business.domain.entity.OrderApproval;
import com.mptc.training.ecommerce.order.domain.valueobject.BusinessId;

import java.time.ZonedDateTime;
import java.util.List;

public abstract class OrderApprovedEvent extends OrderApprovalEvent {
    public OrderApprovedEvent(OrderApproval orderApproval, BusinessId businessId, List<String> failureMessages, ZonedDateTime createdAt) {
        super(orderApproval, businessId, failureMessages, createdAt);
    }
}
