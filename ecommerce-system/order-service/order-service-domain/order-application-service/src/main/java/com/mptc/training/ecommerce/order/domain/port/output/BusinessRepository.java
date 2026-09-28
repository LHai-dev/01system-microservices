package com.mptc.training.ecommerce.order.domain.port.output;

import com.mptc.training.ecommerce.order.domain.entity.Business;

import java.util.Optional;

public interface BusinessRepository {

    Optional<Business> findBusiness(Business business);

}