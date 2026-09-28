package com.mptc.training.ecommerce.restapi.dto;

public record FieldErrorResponse(
        String field,
        String code,
        String message
) {
}
