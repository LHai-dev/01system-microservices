package com.mptc.training.ecommerce.order.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Order_Addresses")
public class OrderAddressEntity {
    @Id
    private UUID id;
    private String street;
    private String postalCode;
    private String city;


    @OneToOne(mappedBy = "orderAddress")
    private OrderEntity order;


}
