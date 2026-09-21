package com.mptc.training.ecommerce.order.domain.port.input;

import com.mptc.training.ecommerce.order.domain.dto.CreateOrderCommand;

public interface ExplicitPort {

    void execute(CreateOrderCommand createOrderCommand);

}
