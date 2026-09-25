package com.mptc.training.ecommerce.order.domain.port.output;

import com.mptc.training.ecommerce.order.domain.entity.Order;

public interface OrderRepository {

    Order saveOrder(Order order);

}
