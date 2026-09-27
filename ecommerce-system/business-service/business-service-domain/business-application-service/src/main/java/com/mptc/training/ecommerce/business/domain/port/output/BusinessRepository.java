package com.mptc.training.ecommerce.business.domain.port.output;

// Placeholder for M4 (business-application-service) — delete once the real port is merged.
import com.mptc.training.ecommerce.business.domain.entity.Business;

import java.util.Optional;

public interface BusinessRepository {

    Optional<Business> findBusinessInformation(Business business);

}
