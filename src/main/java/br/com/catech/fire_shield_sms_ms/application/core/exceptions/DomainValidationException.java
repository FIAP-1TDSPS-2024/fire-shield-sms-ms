package br.com.catech.fire_shield_sms_ms.application.core.exceptions;

public class DomainValidationException extends RuntimeException {

    public DomainValidationException(String message) {
        super(message);
    }
}

