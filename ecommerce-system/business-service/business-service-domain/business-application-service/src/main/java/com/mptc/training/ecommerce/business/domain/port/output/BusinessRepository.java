package com.mptc.training.ecommerce.business.domain.port.output;

import com.mptc.training.ecommerce.business.domain.entity.Business;

import java.util.Optional;

public interface BusinessRepository {

    Optional<Business> findBusinessInformation(Business business);

}
