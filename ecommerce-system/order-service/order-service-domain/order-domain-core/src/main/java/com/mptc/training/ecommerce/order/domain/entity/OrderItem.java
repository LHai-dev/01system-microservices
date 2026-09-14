package com.mptc.training.ecommerce.order.domain.entity;

import com.mptc.training.ecommerce.order.persistence.domain.entity.BaseEntity;
import com.mptc.training.ecommerce.order.persistence.domain.valueobject.Money;
import com.mptc.training.ecommerce.order.persistence.domain.valueobject.OrderId;
import com.mptc.training.ecommerce.order.persistence.domain.valueobject.OrderItemId;

public class OrderItem extends BaseEntity<OrderItemId> {
    private final Product product;
    private final Integer quantity;
    private final Money price;
    private final Money subTotal;
    private OrderId orderId;

    public Product getProduct() {
        return product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Money getPrice() {
        return price;
    }

    public Money getSubTotal() {
        return subTotal;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public void setOrderId(OrderId orderId) {
        this.orderId = orderId;
    }

    private OrderItem(Builder builder) {
        super.setId(builder.id);
        product = builder.product;
        quantity = builder.quantity;
        price = builder.price;
        subTotal = builder.subTotal;
        orderId = builder.orderId;
    }

    void initializeOrderItem(OrderId orderid, OrderItemId orderItemId) {
        this.orderId = orderid;
        super.setId(orderItemId);
    }

    boolean isPriceValid() {
        return price.isGreaterThanZero() && price.equals(product.getPrice()) && price.multiply(quantity).equals(subTotal);
    }


    public static final class Builder {
        private OrderItemId id;
        private Product product;
        private Integer quantity;
        private Money price;
        private Money subTotal;
        private OrderId orderId;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder id(OrderItemId val) {
            id = val;
            return this;
        }

        public Builder product(Product val) {
            product = val;
            return this;
        }

        public Builder quantity(Integer val) {
            quantity = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder subTotal(Money val) {
            subTotal = val;
            return this;
        }

        public Builder orderId(OrderId val) {
            orderId = val;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}
