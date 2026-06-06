package br.com.catech.fire_shield_sms_ms.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LoginRequest", description = "Dados de autenticacao do usuario da aplicacao.")
public record LoginRequest(
        @Schema(description = "Nome do usuario", example = "admin")
        String username,

        @Schema(description = "Senha do usuario", example = "admin123")
        String password
) {
}
