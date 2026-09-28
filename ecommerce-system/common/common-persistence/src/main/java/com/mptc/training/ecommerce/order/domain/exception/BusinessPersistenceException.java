package com.mptc.training.ecommerce.order.domain.exception;

public class BusinessPersistenceException extends RuntimeException {
    public BusinessPersistenceException(String message) {
        super(message);
    }
}