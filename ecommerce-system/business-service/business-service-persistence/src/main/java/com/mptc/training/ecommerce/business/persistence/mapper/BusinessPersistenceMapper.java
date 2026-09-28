package com.mptc.training.ecommerce.business.persistence.mapper;

import com.mptc.training.ecommerce.business.domain.entity.Business;
import com.mptc.training.ecommerce.business.domain.entity.OrderDetail;
import com.mptc.training.ecommerce.business.domain.entity.Product;
import com.mptc.training.ecommerce.business.persistence.entity.BusinessEntity;
import com.mptc.training.ecommerce.order.domain.exception.BusinessPersistenceException;
import com.mptc.training.ecommerce.order.domain.valueobject.BusinessId;
import com.mptc.training.ecommerce.order.domain.valueobject.Money;
import com.mptc.training.ecommerce.order.domain.valueobject.ProductId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BusinessPersistenceMapper {

    @Mapping(source = "id", target = "id.value")
    Business businessEntityToBusiness(BusinessEntity businessEntity);

}
