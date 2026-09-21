package com.mptc.training.ecommerce.order.restapi.controller;

import com.mptc.training.ecommerce.order.domain.dto.CreateOrderCommand;
import com.mptc.training.ecommerce.order.domain.dto.CreateOrderResult;
import com.mptc.training.ecommerce.order.domain.port.input.CreateOrderUseCase;
import com.mptc.training.ecommerce.order.restapi.dto.OrderCreateRequest;
import com.mptc.training.ecommerce.order.restapi.dto.OrderCreateResponse;
import com.mptc.training.ecommerce.order.restapi.mapper.OrderWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
    
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderCommandController {

    private final CreateOrderUseCase createOrderUseCase;
    private final OrderWebMapper orderWebMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderCreateResponse createOrder(@Valid @RequestBody OrderCreateRequest order) {
        CreateOrderCommand createOrderCommand = orderWebMapper.orderCreateRequestToCreateOrderCommand(order);
        CreateOrderResult createOrderResult = createOrderUseCase.execute(createOrderCommand);

        return orderWebMapper.createOrderResultToOrderCreateResponse(createOrderResult);
    }

}
