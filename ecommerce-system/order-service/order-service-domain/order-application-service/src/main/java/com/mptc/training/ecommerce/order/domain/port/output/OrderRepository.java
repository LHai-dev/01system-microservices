package com.mptc.training.ecommerce.order.domain.output;

import com.mptc.training.ecommerce.order.domain.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository {
    void saveOrder(Order order);
}
