package com.mptc.training.ecommerce.order.domain.entity;

import com.mptc.training.ecommerce.order.persistence.domain.entity.AggregateRoot;
import com.mptc.training.ecommerce.order.persistence.domain.valueobject.CustomerId;

public class Customer extends AggregateRoot<CustomerId> {

    private final String username;
    private final String familyName;
    private final String givenName;

    private Customer(Builder builder) {
        super.setId(builder.id);
        this.username = builder.username;
        this.familyName = builder.familyName;
        this.givenName = builder.givenName;
    }

    public String getUsername() {
        return username;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getGivenName() {
        return givenName;
    }

    public static final class Builder {

        private CustomerId id;
        private String username;
        private String familyName;
        private String givenName;

        private Builder() {}

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder id(CustomerId id) {
            this.id = id;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder familyName(String familyName) {
            this.familyName = familyName;
            return this;
        }

        public Builder givenName(String givenName) {
            this.givenName = givenName;
            return this;
        }

        public Customer build() {
            return new Customer(this);
        }
    }
}