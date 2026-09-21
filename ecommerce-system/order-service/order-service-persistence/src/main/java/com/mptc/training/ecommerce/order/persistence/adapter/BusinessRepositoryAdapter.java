package com.mptc.training.ecommerce.order.persistence.adapter;

import com.mptc.training.ecommerce.order.domain.entity.Business;
import com.mptc.training.ecommerce.order.domain.port.output.BusinessRepository;
import com.mptc.training.ecommerce.order.domain.valueobject.BusinessId;
import com.mptc.training.ecommerce.order.persistence.mapper.BusinessPersistenceMapper;
import com.mptc.training.ecommerce.order.persistence.repository.BusinessJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BusinessRepositoryAdapter implements BusinessRepository {

    private final BusinessJpaRepository businessJpaRepository;
    private final BusinessPersistenceMapper businessPersistenceMapper;

    @Override
    public Optional<Business> findBusiness(BusinessId businessId) {
        return businessJpaRepository
                .findByBusinessId(businessId.value())
                .map(businessPersistenceMapper::businessEntityToBusiness);
    }
}
