package com.mptc.training.ecommerce.order.persistence.domain.port.input;

import com.mptc.training.ecommerce.order.persistence.domain.port.dto.CreateOrderRequest;

public interface CreateOrderUseCase {

    void execute(CreateOrderRequest createOrderRequest);
}
