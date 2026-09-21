package com.mptc.training.ecommerce.order.persistence.mapper;

import com.mptc.training.ecommerce.order.domain.entity.Customer;
import com.mptc.training.ecommerce.order.domain.entity.Order;
import com.mptc.training.ecommerce.order.persistence.entity.CustomerEntity;
import com.mptc.training.ecommerce.order.persistence.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface OrderPersistenceMapper {

//    @Mapping(source = "id.value", target = "id")
//    @Mapping(source = "customerId", target = "customerId.value")
//    OrderEntity orderToOrderEntity(Order order);
//
//    @Mapping(source = "id", target = "id.value")
//    @Mapping(source = "customerId", target = "customerId.value")
//    Order orderEntityToOrder(OrderEntity orderEntity);
//
//    @Mapping(source = "id.value", target = "id")
//    CustomerEntity customerToCustomerEntity(Customer customer);

    @Mapping(source = "id", target = "id.value")
    Customer customerEntityToCustomer(CustomerEntity customerEntity);
}