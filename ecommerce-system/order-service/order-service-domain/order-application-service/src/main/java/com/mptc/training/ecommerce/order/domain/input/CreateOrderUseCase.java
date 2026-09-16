package com.mptc.training.ecommerce.order.domain.input;

import com.mptc.training.ecommerce.order.domain.dto.CreateOrderRequest;

public interface CreateOrderUseCase {

    void execute(CreateOrderRequest createOrderRequest);
}
