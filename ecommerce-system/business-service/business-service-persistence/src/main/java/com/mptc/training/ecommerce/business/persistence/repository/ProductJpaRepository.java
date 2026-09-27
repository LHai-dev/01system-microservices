package com.mptc.training.ecommerce.business.persistence.repository;

import com.mptc.training.ecommerce.business.persistence.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, UUID> {

    List<ProductEntity> findByBusiness_Id(UUID businessId);
}
