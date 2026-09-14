package com.mptc.training.ecommerce.order.domain;

import com.mptc.training.ecommerce.order.domain.entity.Business;
import com.mptc.training.ecommerce.order.domain.entity.Order;
import com.mptc.training.ecommerce.order.domain.event.OrderCancelledEvent;
import com.mptc.training.ecommerce.order.domain.event.OrderCreatedEvent;
import com.mptc.training.ecommerce.order.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {
    OrderCreatedEvent validateAndInitiateOrder(Order order, Business business);

    OrderPaidEvent payOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessage);

    void cancelOrder(Order order, List<String> failureMessage);
}
