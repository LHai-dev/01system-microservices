package com.mptc.training.ecommerce.order.domain.mapper;

import com.mptc.training.ecommerce.order.domain.dto.CommandOrderItem;
import com.mptc.training.ecommerce.order.domain.dto.CreateOrderCommand;
import com.mptc.training.ecommerce.order.domain.entity.Order;
import com.mptc.training.ecommerce.order.domain.entity.OrderItem;
import com.mptc.training.ecommerce.order.domain.usecase.CreateOrderUseCase;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderDomainMapper {


    @Mapping(source = "customerId",target = "customerId.value")
    @Mapping(source = "businessId",target = "businessId.value")
    @Mapping(source = "price",target = "price.amount")
    Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand);

    @Mapping(source = "productId",target = "product.id.value")
    @Mapping(source = "price",target = "price.amount")
    @Mapping(source = "subTotal",target = "subTotal.amount")
    OrderItem commandOrderItemToOrderItem(CommandOrderItem commandOrderItem);
}
