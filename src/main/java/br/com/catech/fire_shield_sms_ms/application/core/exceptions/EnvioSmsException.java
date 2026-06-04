package br.com.catech.fire_shield_sms_ms.application.core.exceptions;

public class EnvioSmsException extends RuntimeException {
    public EnvioSmsException(String message) {
        super(message);
    }
}
