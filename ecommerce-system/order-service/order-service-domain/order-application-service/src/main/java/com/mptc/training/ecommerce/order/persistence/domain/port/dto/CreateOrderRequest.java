package com.mptc.training.ecommerce.order.persistence.domain.port.dto;

import com.mptc.training.ecommerce.order.persistence.domain.valueobject.BusinessId;
import com.mptc.training.ecommerce.order.persistence.domain.valueobject.CustomerId;
import com.mptc.training.ecommerce.order.persistence.domain.valueobject.Money;
import com.mptc.training.ecommerce.order.persistence.domain.valueobject.StreetAddress;

public record CreateOrderRequest(
        CustomerId customerId,
        BusinessId businessId,
        StreetAddress deliveryAddress,
        Money price

) {
}
