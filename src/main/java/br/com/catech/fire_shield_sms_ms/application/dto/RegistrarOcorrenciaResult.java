package br.com.catech.fire_shield_sms_ms.application.dto;

import java.util.UUID;

public record RegistrarOcorrenciaResult(UUID ocorrenciaId, int smsEnviados) {
}

