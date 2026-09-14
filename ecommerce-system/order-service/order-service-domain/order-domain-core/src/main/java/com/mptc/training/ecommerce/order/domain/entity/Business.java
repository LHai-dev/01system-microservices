package com.mptc.training.ecommerce.order.domain.entity;

import com.mptc.training.ecommerce.order.persistence.domain.entity.AggregateRoot;
import com.mptc.training.ecommerce.order.persistence.domain.valueobject.BusinessId;

import java.util.List;

public class Business extends AggregateRoot<BusinessId> {
    private final List<Product> products;
    private boolean active;
    public Business(List<Product> products) {
        this.products = products;
    }

    private Business(Builder builder) {
        super.setId(builder.id);
        products = builder.products;
        active = builder.active;
    }

    public boolean isActive(){
        return active;
    }

    public static final class Builder {
        private BusinessId id;
        private List<Product> products;
        private boolean active;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder id(BusinessId val) {
            id = val;
            return this;
        }

        public Builder products(List<Product> val) {
            products = val;
            return this;
        }

        public Builder active(boolean val) {
            active = val;
            return this;
        }

        public Business build() {
            return new Business(this);
        }
    }
}
