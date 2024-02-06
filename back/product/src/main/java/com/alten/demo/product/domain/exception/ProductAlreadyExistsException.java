package com.alten.demo.product.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ProductAlreadyExistsException extends ResponseStatusException {

    public ProductAlreadyExistsException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }

    public ProductAlreadyExistsException(String reason, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, reason, cause);
    }
}
