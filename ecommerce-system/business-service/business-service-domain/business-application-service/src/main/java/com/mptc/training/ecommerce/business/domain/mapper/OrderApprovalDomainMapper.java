package com.mptc.training.ecommerce.business.domain.mapper;

import com.mptc.training.ecommerce.business.domain.dto.OrderApprovalCommand;
import com.mptc.training.ecommerce.business.domain.entity.OrderApproval;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderApprovalDomainMapper {

    @Mapping(source = "businessId", target = "businessId.value")
    @Mapping(source = "orderId", target = "orderId.value")
    @Mapping(source = "approvalStatus", target = "approvalStatus")
    OrderApproval orderApprovalCommandToOrderApproval(OrderApprovalCommand orderApprovalCommand);

    @Mapping(source = "businessId.value", target = "businessId")
    @Mapping(source = "orderId.value", target = "orderId")
    @Mapping(source = "approvalStatus", target = "approvalStatus")
    OrderApprovalCommand orderApprovalToOrderApprovalCommand(
            OrderApproval orderApproval
    );

}
