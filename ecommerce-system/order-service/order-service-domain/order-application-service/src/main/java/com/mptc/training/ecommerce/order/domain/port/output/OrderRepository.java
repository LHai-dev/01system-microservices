package com.mptc.training.ecommerce.order.domain.port.output;

import com.mptc.training.ecommerce.order.domain.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository {
    Order saveOrder(Order order);
}
