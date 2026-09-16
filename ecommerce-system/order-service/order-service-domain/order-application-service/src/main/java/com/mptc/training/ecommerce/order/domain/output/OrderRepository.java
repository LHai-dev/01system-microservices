package com.mptc.training.ecommerce.order.domain.output;

import com.mptc.training.ecommerce.order.domain.entity.Order;

public interface OrderRepository {
    void saveOrder(Order order);
}
