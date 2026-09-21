package com.mptc.training.ecommerce.order.persistence.repository;

import com.mptc.training.ecommerce.order.persistence.entity.BusinessEntity;
import com.mptc.training.ecommerce.order.persistence.entity.BusinessIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface BusinessJpaRepository extends JpaRepository<BusinessEntity, BusinessIdEntity> {

    Optional<BusinessEntity> findByBusinessId(UUID businessId);
}
