package br.com.catech.fire_shield_sms_ms.application.core.entities;

import java.time.Instant;

public record TokenAcesso(
        String token,
        Instant expiresAt
) {
}

