package com.mptc.training.ecommerce.order.persistence.mapper;

import com.mptc.training.ecommerce.order.domain.entity.Customer;
import com.mptc.training.ecommerce.order.domain.entity.Order;
import com.mptc.training.ecommerce.order.domain.entity.OrderItem;
import com.mptc.training.ecommerce.order.domain.valueobject.StreetAddress;
import com.mptc.training.ecommerce.order.persistence.entity.CustomerEntity;
import com.mptc.training.ecommerce.order.persistence.entity.OrderAddressEntity;
import com.mptc.training.ecommerce.order.persistence.entity.OrderEntity;
import com.mptc.training.ecommerce.order.persistence.entity.OrderItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderPersistenceMapper {

    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "customerId.value", target = "customerId")
    @Mapping(source = "businessId.value", target = "businessId")
    @Mapping(source = "deliveryAddress", target = "orderAddress")
    @Mapping(source = "items", target = "orders")
    @Mapping(source = "trackingId.value", target = "trackingId")
    @Mapping(target = "failureMessages",
            expression = "java(order.getFailureMessages() == null ? null : String.join(\";\", order.getFailureMessages()))")
    OrderEntity orderToEntity(Order order);

    OrderAddressEntity addressToEntity(StreetAddress streetAddress);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "product.id.value", target = "productId")
    @Mapping(source = "price.amount", target = "price")
    @Mapping(source = "subTotal.amount", target = "subtotal")
    OrderItemEntity orderItemToEntity(OrderItem orderItem);

    @Mapping(source = "id", target = "id.value")
    Customer customerEntityToCustomer(CustomerEntity customerEntity);
}
