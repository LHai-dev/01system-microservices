package com.mptc.training.ecommerce.order.restapi.mapper;

import com.mptc.training.ecommerce.order.domain.dto.CreateOrderCommand;
import com.mptc.training.ecommerce.order.domain.dto.CreateOrderResult;
import com.mptc.training.ecommerce.order.restapi.dto.OrderCreateRequest;
import com.mptc.training.ecommerce.order.restapi.dto.OrderCreateResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderWebMapper {

    // Source = OrderCreateRequest
    // Target = CreateOrderCommand
    CreateOrderCommand orderCreateRequestToCreateOrderCommand(
            OrderCreateRequest orderCreateRequest
    );

    OrderCreateResponse createOrderResultToOrderCreateResponse(
            CreateOrderResult createOrderResult
    );

}