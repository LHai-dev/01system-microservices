package com.mptc.training.ecommerce.order.domain.input;

import com.mptc.training.ecommerce.order.domain.dto.CreateOrderCommand;
import com.mptc.training.ecommerce.order.domain.dto.CreateOrderResult;

public interface CreateOrderUseCase {

    CreateOrderResult execute(CreateOrderCommand createOrderCommand);
}
