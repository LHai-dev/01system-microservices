package com.mptc.training.ecommerce.order.domain.usecase;

import com.mptc.training.ecommerce.order.domain.dto.CreateOrderCommand;
import com.mptc.training.ecommerce.order.domain.dto.CreateOrderResult;
import com.mptc.training.ecommerce.order.domain.port.input.CreateOrderUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class CreateOrderUseCaseImpl implements CreateOrderUseCase {

    @Override
    public CreateOrderResult execute(CreateOrderCommand createOrderCommand) {
        log.info("create order Use Case. {}", createOrderCommand);
        return new CreateOrderResult(UUID.randomUUID());
    }
}