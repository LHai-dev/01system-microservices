package com.mptc.training.ecommerce.order.persistence.adapter;

import com.mptc.training.ecommerce.order.persistence.domain.port.output.OrderRepository;
import com.mptc.training.ecommerce.order.domain.entity.Order;
import com.mptc.training.ecommerce.order.persistence.repository.OrderJpaRepository;

public class OrderRepositoryAdapter implements OrderRepository {
    private final OrderJpaRepository orderJpaRepository;

    public OrderRepositoryAdapter(OrderJpaRepository orderJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
    }

    @Override
    public void saveOrder(Order order) {

    }
}
