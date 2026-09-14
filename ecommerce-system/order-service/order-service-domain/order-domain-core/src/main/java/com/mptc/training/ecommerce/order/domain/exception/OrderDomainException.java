package com.mptc.training.ecommerce.order.domain.exception;

import com.mptc.training.ecommerce.order.persistence.domain.exception.DomainException;

public class OrderDomainException extends DomainException {
    public OrderDomainException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderDomainException(String message) {
        super(message);
    }
}
