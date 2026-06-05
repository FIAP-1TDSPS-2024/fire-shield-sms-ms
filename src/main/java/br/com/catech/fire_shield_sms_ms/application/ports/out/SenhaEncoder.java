package br.com.catech.fire_shield_sms_ms.application.ports.out;

public interface SenhaEncoder {
    boolean matches(String rawPassword, String encodedPassword);
}

