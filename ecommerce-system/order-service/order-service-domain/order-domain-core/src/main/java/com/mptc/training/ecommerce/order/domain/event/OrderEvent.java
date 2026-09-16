package com.mptc.training.ecommerce.order.domain.event;

import com.mptc.training.ecommerce.order.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderEvent implements DomainEvent<Order> {
    private final Order order;
    private final ZonedDateTime createdAt;

    public OrderEvent(Order order, ZonedDateTime createdAt) {
        this.order = order;
        this.createdAt = createdAt;
    }

    public Order getOrder() {
        return order;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }
}
