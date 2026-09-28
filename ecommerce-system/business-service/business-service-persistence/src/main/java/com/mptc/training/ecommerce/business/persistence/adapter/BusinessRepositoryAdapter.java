package com.mptc.training.ecommerce.business.persistence.adapter;

import com.mptc.training.ecommerce.business.domain.entity.Business;
import com.mptc.training.ecommerce.business.domain.port.output.BusinessRepository;
import com.mptc.training.ecommerce.business.persistence.entity.BusinessEntity;
import com.mptc.training.ecommerce.business.persistence.mapper.BusinessPersistenceMapper;
import com.mptc.training.ecommerce.business.persistence.repository.BusinessJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BusinessRepositoryAdapter implements BusinessRepository {

    private final BusinessJpaRepository businessJpaRepository;
    private final BusinessPersistenceMapper businessPersistenceMapper;

    @Override
    public Optional<Business> findBusiness(UUID businessId) {
        return businessJpaRepository.findById(businessId)
                .map(businessPersistenceMapper::businessEntityToBusiness);
    }
//
//    private final CustomerJpaRepository customerJpaRepository;
//    private final CustomerPersistenceMapper customerPersistenceMapper;
//
//    @Override
//    public Optional<Customer> findCustomer(UUID customerId) {
//        return customerJpaRepository.findById(customerId)
//                .map(customerPersistenceMapper::customerEntityToCustomer);
//    }
}
