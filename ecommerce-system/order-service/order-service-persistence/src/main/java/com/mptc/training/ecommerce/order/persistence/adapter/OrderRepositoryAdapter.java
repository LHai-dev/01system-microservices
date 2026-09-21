package com.mptc.training.ecommerce.order.persistence.adapter;

import com.mptc.training.ecommerce.order.domain.port.output.OrderRepository;
import com.mptc.training.ecommerce.order.domain.entity.Order;
import com.mptc.training.ecommerce.order.persistence.entity.OrderEntity;
import com.mptc.training.ecommerce.order.persistence.mapper.OrderPersistenceMapper;
import com.mptc.training.ecommerce.order.persistence.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderPersistenceMapper orderMapper;

    @Override
    public Order saveOrder(Order order) {
        OrderEntity orderEntity = orderMapper.orderToEntity(order);
        orderJpaRepository.save(orderEntity);
        return order;
    }
}