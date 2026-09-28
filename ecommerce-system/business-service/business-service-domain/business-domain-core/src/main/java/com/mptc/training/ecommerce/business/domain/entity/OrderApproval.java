package com.mptc.training.ecommerce.business.domain.entity;

import com.mptc.training.ecommerce.domain.valueobject.OrderApprovalId;
import com.mptc.training.ecommerce.domain.valueobject.OrderApprovalStatus;
import com.mptc.training.ecommerce.domain.entity.BaseEntity;
import com.mptc.training.ecommerce.domain.valueobject.BusinessId;
import com.mptc.training.ecommerce.domain.valueobject.OrderId;

public class OrderApproval extends BaseEntity<OrderApprovalId> {
    private final BusinessId businessId;
    private final OrderId orderId;
    private final OrderApprovalStatus approvalStatus;

    private OrderApproval(Builder builder) {
        super.setId(builder.id);
        businessId = builder.businessId;
        orderId = builder.orderId;
        approvalStatus = builder.approvalStatus;
    }

    public static Builder builder() {
        return new Builder();
    }

    public BusinessId getBusinessId() {
        return businessId;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public OrderApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }


    public static final class Builder {
        private OrderApprovalId id;
        private BusinessId businessId;
        private OrderId orderId;
        private OrderApprovalStatus approvalStatus;

        private Builder() {
        }

        public Builder id(OrderApprovalId val) {
            id = val;
            return this;
        }

        public Builder businessId(BusinessId val) {
            businessId = val;
            return this;
        }

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public Builder approvalStatus(OrderApprovalStatus val) {
            approvalStatus = val;
            return this;
        }

        public OrderApproval build() {
            return new OrderApproval(this);
        }
    }
}
