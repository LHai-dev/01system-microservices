package com.mptc.training.ecommerce.order.domain.dto;

import com.mptc.training.ecommerce.domain.valueobject.BusinessId;
import com.mptc.training.ecommerce.domain.valueobject.CustomerId;
import com.mptc.training.ecommerce.domain.valueobject.Money;
import com.mptc.training.ecommerce.domain.valueobject.StreetAddress;

public record CreateOrderRequest(
        CustomerId customerId,
        BusinessId businessId,
        StreetAddress deliveryAddress,
        Money price

) {
}
