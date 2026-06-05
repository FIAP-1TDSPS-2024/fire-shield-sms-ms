package br.com.catech.fire_shield_sms_ms.adapter.in.web.dto;

import java.time.Instant;

public record LoginResponse(
        String tokenType,
        String accessToken,
        Instant expiresAt
) {
}

