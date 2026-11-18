package com.mptc.training.ecommerce.user.exception;

import com.mptc.training.ecommerce.restapi.exception.GlobalException;
import org.springframework.http.HttpStatus;

public class KeyCloakException extends GlobalException {

    public KeyCloakException(HttpStatus status, String message) {
        super(status, message);
    }
}
