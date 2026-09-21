package com.mptc.training.ecommerce.order.restapi.controller;

import com.mptc.training.ecommerce.order.restapi.dto.OrderCreateRequest;
import com.mptc.training.ecommerce.order.restapi.dto.OrderCreateResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderCreateResponse createOrder(@Valid @RequestBody OrderCreateRequest order) {
        //response entity if you want to implement some logic depend on your business logic
        return OrderCreateResponse.builder().orderId(UUID.randomUUID()).build();
    }

}
