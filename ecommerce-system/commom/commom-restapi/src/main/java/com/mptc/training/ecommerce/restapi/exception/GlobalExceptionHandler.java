package com.mptc.training.ecommerce.restapi.exception;

import com.mptc.training.ecommerce.restapi.dto.FieldErrorResponse;
import com.mptc.training.ecommerce.restapi.dto.RestApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public RestApiErrorResponse<?> handlerException(MethodArgumentNotValidException e) {
        return RestApiErrorResponse.builder()
                .code(HttpStatus.BAD_GATEWAY.getReasonPhrase())
                .message("Data validation failed")
                .detail(extractFieldErrors(e.getFieldErrors()))
                .build();
    }


    private List<FieldErrorResponse> extractFieldErrors(
            List<FieldError> fieldErrors
    ) {
        return fieldErrors.stream().map(fieldError -> new FieldErrorResponse(
                fieldError.getField(),
                fieldError.getCode(),
                fieldError.getDefaultMessage()
        )).toList();
    }
}
