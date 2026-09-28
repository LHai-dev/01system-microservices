package com.mptc.training.ecommerce.domain.exception;

public class BusinessPersistenceException extends RuntimeException {
    public BusinessPersistenceException(String message) {
        super(message);
    }
}