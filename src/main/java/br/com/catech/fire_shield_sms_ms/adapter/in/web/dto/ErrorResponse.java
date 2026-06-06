package br.com.catech.fire_shield_sms_ms.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@Schema(name = "ErrorResponse", description = "Resposta padronizada para erros da API.")
public record ErrorResponse(
        @Schema(description = "Data e hora do erro", example = "2026-06-05T11:53:43.8812128")
        LocalDateTime timestamp,

        @Schema(description = "Status HTTP", example = "401")
        int status,

        @Schema(description = "Descricao resumida do erro", example = "Unauthorized")
        String error,

        @Schema(description = "Mensagens detalhadas do erro", example = "[\"credenciais invalidas\"]")
        List<String> messages
) {
    public static ErrorResponse of(int status, String error, String message) {
        return new ErrorResponse(LocalDateTime.now(), status, error, List.of(message));
    }

    public static ErrorResponse of(int status, String error, List<String> messages) {
        return new ErrorResponse(LocalDateTime.now(), status, error, messages);
    }
}
