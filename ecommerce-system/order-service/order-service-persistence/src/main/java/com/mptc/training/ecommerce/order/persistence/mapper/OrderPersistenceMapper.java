package com.mptc.training.ecommerce.order.persistence.mapper;

import com.mptc.training.ecommerce.order.domain.entity.Customer;
import com.mptc.training.ecommerce.order.persistence.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;



@Mapper(componentModel = "spring")
public interface OrderPersistenceMapper {

    @Mapping(source = "id", target = "id.value")
    Customer customerEntityToCustomer(CustomerEntity customerEntity);


}
