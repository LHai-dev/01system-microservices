package com.mptc.training.ecommerce.order.domain.port.input;

import com.mptc.training.ecommerce.order.domain.dto.CreateOrderCommand;
import com.mptc.training.ecommerce.order.domain.dto.CreateOrderResult;

public interface CreateOrderUseCase {

    CreateOrderResult execute(CreateOrderCommand createOrderCommand);
}
