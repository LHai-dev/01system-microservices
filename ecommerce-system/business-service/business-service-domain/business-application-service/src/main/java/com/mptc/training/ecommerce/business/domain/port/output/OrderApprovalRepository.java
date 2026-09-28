package com.mptc.training.ecommerce.business.domain.port.output;

import com.mptc.training.ecommerce.business.domain.entity.OrderApproval;

public interface OrderApprovalRepository {

    OrderApproval save(OrderApproval orderApproval);

}
