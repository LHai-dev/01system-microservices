package com.mptc.training.ecommerce.order.persistence.adapter;

import com.mptc.training.ecommerce.order.domain.entity.Order;
import com.mptc.training.ecommerce.order.domain.port.output.OrderRepository;
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
        OrderEntity orderEntity = orderMapper.orderToOrderEntity(order);

        // Back-references must be set before save: order is part of OrderItemEntity's composite id.
        orderEntity.getOrderAddress().setOrder(orderEntity);
        orderEntity.getItems().forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));

        return orderMapper.orderEntityToOrder(orderJpaRepository.save(orderEntity));
    }
}