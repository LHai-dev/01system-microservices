package com.mptc.training.ecommerce.order.persistence.mapper;

import com.mptc.training.ecommerce.order.domain.entity.Business;
import com.mptc.training.ecommerce.order.persistence.entity.BusinessEntity;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface BusinessPersistenceMapper {

    BusinessEntity businessToBusinessEntity(Business business);

    Business businessEntityToBusiness(BusinessEntity businessEntity);
}