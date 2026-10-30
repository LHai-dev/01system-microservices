package com.mptc.training.ecommerce.order.domain.event;

import com.mptc.training.ecommerce.order.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderPaidEvent extends OrderEvent{
    public OrderPaidEvent(Order order, ZonedDateTime createdAt){
        super(order,createdAt);
    }
}
