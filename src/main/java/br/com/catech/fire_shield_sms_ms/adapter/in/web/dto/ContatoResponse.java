package br.com.catech.fire_shield_sms_ms.adapter.in.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(name = "ContatoResponse", description = "Contato cadastrado para notificacao por estado.")
public record ContatoResponse(
        @Schema(description = "Identificador do contato", example = "0190b1f5-8d5c-7d75-9f46-1e9e5f0a0001")
        UUID uuid,

        @Schema(description = "Nome do orgao ou contato responsavel", example = "Defesa Civil de Sao Paulo")
        String nome,

        @Schema(description = "Descricao complementar do contato", example = "Plantao estadual para ocorrencias de incendio")
        String descricao,

        @Schema(description = "Numero de telefone no formato E.164", example = "+5511999999999")
        String numero,

        @Schema(description = "Estado atendido pelo contato", example = "Sao Paulo")
        String estado
) {
}
