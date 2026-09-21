package com.mptc.training.ecommerce.order.persistence.adapter;

import com.mptc.training.ecommerce.order.domain.entity.Business;
import com.mptc.training.ecommerce.order.domain.port.output.BusinessRepository;
import com.mptc.training.ecommerce.order.domain.valueobject.BusinessId;
import com.mptc.training.ecommerce.order.persistence.mapper.OrderPersistenceMapper;
import com.mptc.training.ecommerce.order.persistence.repository.BusinessJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BusinessRepositoryAdapter implements BusinessRepository {

    private final BusinessJpaRepository businessJpaRepository;
    private final OrderPersistenceMapper orderPersistenceMapper;

    @Override
    public Optional<Business> findBusiness(BusinessId businessId) {
//        Optional<BusinessEntity> businessEntities = businessJpaRepository.findByBusinessId(businessId.value());
//        return Optional.ofNullable(orderPersistenceMapper.businessEntitiesToBusiness(businessId.value(), businessEntities));
        return null;
    }
}
