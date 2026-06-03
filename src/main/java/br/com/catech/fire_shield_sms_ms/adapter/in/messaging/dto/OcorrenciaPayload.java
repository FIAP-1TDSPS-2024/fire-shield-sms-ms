package br.com.catech.fire_shield_sms_ms.adapter.in.messaging.dto;

import br.com.catech.fire_shield_sms_ms.application.core.enums.SeveridadeOcorrenciaEnum;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

/**
 * DTO de entrada recebido via RabbitMQ.
 * Campos espelham os campos da entidade {@code Ocorrencia}.
 * Campos de endereço são opcionais — ocorrências em área rural podem não tê-los.
 */
public record OcorrenciaPayload(

        @JsonProperty("latitude")
        Double latitude,

        @JsonProperty("longitude")
        Double longitude,

        @JsonProperty("severidade")
        SeveridadeOcorrenciaEnum severidade,

        @JsonProperty("horario_deteccao")
        LocalDateTime horarioDeteccao,

        // Endereço – opcionais
        @JsonProperty("nome")
        String nome,

        @JsonProperty("bairro")
        String bairro,

        @JsonProperty("cidade")
        String cidade,

        @JsonProperty("estado")
        String estado,

        @JsonProperty("cep")
        String cep
) {}
