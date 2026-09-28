package com.mptc.training.ecommerce.business.domain.entity;

import com.mptc.training.ecommerce.business.domain.exception.BusinessDomainException;
import com.mptc.training.ecommerce.business.domain.valueobject.OrderApprovalId;
import com.mptc.training.ecommerce.business.domain.valueobject.OrderApprovalStatus;
import com.mptc.training.ecommerce.order.domain.entity.AggregateRoot;
import com.mptc.training.ecommerce.order.domain.valueobject.BusinessId;
import com.mptc.training.ecommerce.order.domain.valueobject.Money;
import com.mptc.training.ecommerce.order.domain.valueobject.OrderStatus;

import java.util.List;
import java.util.UUID;

public class Business extends AggregateRoot<BusinessId> {
    private final boolean active;
    private final OrderDetail orderDetail;

    private OrderApproval orderApproval;

    public OrderApproval getOrderApproval() {
        return orderApproval;
    }

    public void setOrderApproval(OrderApproval orderApproval) {
        this.orderApproval = orderApproval;
    }

    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public boolean isActive() {
        return active;
    }

    private Business(Builder builder) {
        super.setId(builder.id);
        active = builder.active;
        orderDetail = builder.orderDetail;
        orderApproval = builder.orderApproval;
    }

    // --------- use case -----------
    public void validateOrder(List<String> failureMessages) {
        validateOrderStatus(failureMessages);
        validateProductAvailability(failureMessages);
        validateOrderTotal(failureMessages);
    }

    public void constructOrderApproval(OrderApproval orderApproval) {
        if (getId() != orderApproval.getBusinessId()) {
            throw new BusinessDomainException("Business are not the same");
        }
        OrderApproval tmpOrderApproval = OrderApproval.builder()
                .id(new OrderApprovalId(UUID.randomUUID()))
                .businessId(getId())
                .orderId(orderApproval.getOrderId())
                .approvalStatus(orderApproval.getApprovalStatus())
                .build();

        this.setOrderApproval(tmpOrderApproval);
    }

    // ------- private use case ---------


    private void validateOrderStatus(List<String> failureMessages) {
        if (orderDetail.getOrderStatus() != OrderStatus.PAID) {
            failureMessages.add("Order is not paid");
        }
    }

    private void validateProductAvailability(List<String> failureMessages) {
        orderDetail.getProducts().stream()
                .filter(product -> !product.isAvailable())
                .forEach(product -> failureMessages.add(
                        "Product with id " + product.getId().value() + " is not available"));
    }

    private void validateOrderTotal(List<String> failureMessages) {
        Money productsTotal = orderDetail.getProducts().stream()
                .map(product -> product.getPrice().multiply(product.getQuantity()))
                .reduce(Money.ZERO, Money::add);

        if (!orderDetail.getTotalAmount().equals(productsTotal)) {
            failureMessages.add("Order total amount does not match product total");
        }
    }



    public static final class Builder {
        private BusinessId id;
        private boolean active;
        private OrderDetail orderDetail;
        private OrderApproval orderApproval;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder id(BusinessId val) {
            id = val;
            return this;
        }

        public Builder active(boolean val) {
            active = val;
            return this;
        }

        public Builder orderDetail(OrderDetail val) {
            orderDetail = val;
            return this;
        }

        public Builder orderApproval(OrderApproval val) {
            orderApproval = val;
            return this;
        }

        public Business build() {
            return new Business(this);
        }
    }
}
