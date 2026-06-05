package br.com.catech.fire_shield_sms_ms.adapter.in.web.dto;

public record ContatoRequest(
        String nome,
        String descricao,
        String numero,
        String estado
) {
}
