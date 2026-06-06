package br.com.catech.fire_shield_sms_ms.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.Instant;

@Schema(name = "LoginResponse", description = "Token JWT gerado para acesso aos endpoints protegidos.")
public record LoginResponse(
        @Schema(description = "Tipo do token", example = "Bearer")
        String tokenType,

        @Schema(description = "Token JWT de acesso")
        String accessToken,

        @Schema(description = "Data e hora de expiracao do token", example = "2026-06-05T15:00:00Z")
        Instant expiresAt
) {
}
