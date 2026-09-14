package com.mptc.training.ecommerce.order.domain.entity;

import com.mptc.training.ecommerce.order.persistence.domain.entity.BaseEntity;
import com.mptc.training.ecommerce.order.persistence.domain.valueobject.Money;
import com.mptc.training.ecommerce.order.persistence.domain.valueobject.ProductId;

public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;

    public Product(ProductId productId, String name, Money price) {
        super.setId(productId);
        this.name = name;
        this.price = price;
    }

    public Product(ProductId productId) {
        super.setId(productId);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Money getPrice() {
        return price;
    }

    public void setPrice(Money price) {
        this.price = price;
    }
}
