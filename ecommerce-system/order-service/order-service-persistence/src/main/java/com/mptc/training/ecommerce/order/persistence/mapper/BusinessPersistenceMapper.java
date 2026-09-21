package com.mptc.training.ecommerce.order.persistence.mapper;

import com.mptc.training.ecommerce.order.domain.entity.Business;
import com.mptc.training.ecommerce.order.persistence.entity.BusinessEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BusinessPersistenceMapper {

    Business businessEntityToBusiness(BusinessEntity businessEntity);
}