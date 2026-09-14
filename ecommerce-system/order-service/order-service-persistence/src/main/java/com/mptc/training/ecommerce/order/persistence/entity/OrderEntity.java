package com.mptc.training.ecommerce.order.persistence.entity;

import com.mptc.training.ecommerce.order.persistence.domain.valueobject.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID customerId;
    private UUID businessId;
    @OneToOne
    private OrderAddressEntity orderAddress;

    @OneToMany(mappedBy = "order")
    List<OrderItemEntity> orders;

    private String failureMessages;
    private OrderStatus orderStatus;
    private UUID trackingId;

}
