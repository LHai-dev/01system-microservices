package com.mptc.training.ecommerce.business.persistence.repository;

import com.mptc.training.ecommerce.business.persistence.entity.BusinessEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BusinessJpaRepository extends JpaRepository<BusinessEntity, UUID> {
}
