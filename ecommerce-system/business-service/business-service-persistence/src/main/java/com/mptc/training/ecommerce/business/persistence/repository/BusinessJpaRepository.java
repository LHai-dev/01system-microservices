package com.mptc.training.ecommerce.business.persistence.repository;

import com.mptc.training.ecommerce.business.persistence.entity.BusinessEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BusinessJpaRepository extends JpaRepository<BusinessEntity, UUID> {

    List<BusinessEntity> findByBusinessIdAndProductIdIn(
            UUID businessId,
            List<UUID> productIds
    );

}
