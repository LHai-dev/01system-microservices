package com.mptc.training.ecommerce.business.persistence.entity;

import com.mptc.training.ecommerce.business.domain.entity.Product;
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
@Table(name = "businesses")
public class BusinessEntity {
    @Id
    private UUID id;

    private String name;
    private Boolean active;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    private List<ProductEntity> products;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    private List<OrderDetailEntity> orderDetails;

    @OneToMany(mappedBy = "business", cascade = CascadeType.ALL)
    private List<OrderApprovalEntity> orderApprovals;

}
