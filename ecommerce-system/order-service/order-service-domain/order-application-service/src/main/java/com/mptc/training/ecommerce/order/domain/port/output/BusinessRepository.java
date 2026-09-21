package com.mptc.training.ecommerce.order.domain.port.output;

import com.mptc.training.ecommerce.order.domain.entity.Business;
import com.mptc.training.ecommerce.order.domain.valueobject.BusinessId;

import java.util.Optional;

public interface BusinessRepository {
    Optional<Business> findBusiness(BusinessId businessId);
}
