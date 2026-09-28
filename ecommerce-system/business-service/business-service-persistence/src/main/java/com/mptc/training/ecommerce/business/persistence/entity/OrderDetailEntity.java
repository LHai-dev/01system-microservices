package com.mptc.training.ecommerce.business.persistence.entity;

import com.mptc.training.ecommerce.business.domain.entity.Product;
import com.mptc.training.ecommerce.order.domain.valueobject.Money;
import com.mptc.training.ecommerce.order.domain.valueobject.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_details")
public class OrderDetailEntity {

    @Id
    private UUID id;

    private UUID orderId;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private BigDecimal totalAmount;
    private List<Product> products;

    @ManyToOne
    private BusinessEntity business;

}
