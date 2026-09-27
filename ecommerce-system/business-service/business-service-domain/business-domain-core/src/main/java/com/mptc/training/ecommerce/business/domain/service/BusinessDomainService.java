package com.mptc.training.ecommerce.business.domain.service;

import com.mptc.training.ecommerce.business.domain.entity.Business;
import com.mptc.training.ecommerce.business.domain.event.OrderApprovalEvent;

import java.util.List;

public interface BusinessDomainService {
    OrderApprovalEvent validateOrder(Business business, List<String> failureMessages);
}
