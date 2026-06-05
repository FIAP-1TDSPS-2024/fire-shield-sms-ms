package br.com.catech.fire_shield_sms_ms.adapter.in.web.dto;

import java.util.UUID;

public record ContatoResponse(
        UUID uuid,
        String nome,
        String descricao,
        String numero,
        String estado
) {
}
