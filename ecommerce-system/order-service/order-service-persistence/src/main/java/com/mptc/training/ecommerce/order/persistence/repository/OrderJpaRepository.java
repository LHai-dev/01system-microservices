package com.mptc.training.ecommerce.order.persistence.repository;

import com.mptc.training.ecommerce.order.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, UUID> {
}
