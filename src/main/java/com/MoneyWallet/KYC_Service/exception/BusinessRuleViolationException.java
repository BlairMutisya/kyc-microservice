package com.MoneyWallet.KYC_Service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a request violates a business rule or constraint.
 * The @ResponseStatus causes Spring to return a 409 (CONFLICT) status code automatically.
 */
@ResponseStatus(HttpStatus.CONFLICT)
public class BusinessRuleViolationException extends RuntimeException {
    public BusinessRuleViolationException(String message) {
        super(message);
    }
}