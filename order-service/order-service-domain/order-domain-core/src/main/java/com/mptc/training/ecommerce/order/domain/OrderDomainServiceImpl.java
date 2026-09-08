package com.mptc.training.ecommerce.order.domain;

import com.mptc.training.ecommerce.order.domain.entity.Business;
import com.mptc.training.ecommerce.order.domain.entity.Order;
import com.mptc.training.ecommerce.order.domain.event.OrderCancelledEvent;
import com.mptc.training.ecommerce.order.domain.event.OrderCreatedEvent;
import com.mptc.training.ecommerce.order.domain.event.OrderPaidEvent;

import java.util.List;

public class OrderDomainServiceImpl implements OrderDomainService{
    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Business business) {
        return null;
    }

    @Override
    public OrderPaidEvent payOrder(Order order) {
        return null;
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessage) {
        return null;
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessage) {

    }
}
