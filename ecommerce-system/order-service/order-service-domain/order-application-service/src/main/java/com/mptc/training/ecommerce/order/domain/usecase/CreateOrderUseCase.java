package com.mptc.training.ecommerce.order.domain.usecase;


import com.mptc.training.ecommerce.order.domain.dto.CreateOrderCommand;
import com.mptc.training.ecommerce.order.domain.dto.CreateOrderResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class CreateOrderUseCase {

    public CreateOrderResult execute(CreateOrderCommand createOrderCommand) {
        log.info("executing CreateOrderUseCase: {}", createOrderCommand);

        // Validate customer

        // Validate business

        return new CreateOrderResult(UUID.randomUUID());
    }

}