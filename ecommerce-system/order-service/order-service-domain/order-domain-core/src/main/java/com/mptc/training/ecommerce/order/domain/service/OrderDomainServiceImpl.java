package com.mptc.training.ecommerce.order.domain.service;

import com.mptc.training.ecommerce.order.domain.entity.Business;
import com.mptc.training.ecommerce.order.domain.entity.Order;
import com.mptc.training.ecommerce.order.domain.entity.Product;
import com.mptc.training.ecommerce.order.domain.event.OrderCancelledEvent;
import com.mptc.training.ecommerce.order.domain.event.OrderCreatedEvent;
import com.mptc.training.ecommerce.order.domain.event.OrderPaidEvent;
import com.mptc.training.ecommerce.order.domain.exception.OrderDomainException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class OrderDomainServiceImpl implements OrderDomainService {
    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Business business) {

        if (!business.isActive()) {
            throw new OrderDomainException("Business is not currently active");
        }
        order.getItems().forEach(orderItem -> {
            business.getProducts().forEach(businessProduct -> {
                Product currentProduct = orderItem.getProduct();

                if (businessProduct.equals(currentProduct)) {
                    currentProduct.updateConfirmedNameAndPrice(currentProduct.getName(), currentProduct.getPrice());

                }
            });
        });

        order.validateOrder();
        order.initializeOrder();

        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
    }

    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
    }


    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessage) {
        order.initCancel(failureMessage);
        return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessage) {
        order.cancel(failureMessage);
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
    }
}
