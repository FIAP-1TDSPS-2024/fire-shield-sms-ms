package br.com.catech.fire_shield_sms_ms.adapter.in.web.mapper;

import br.com.catech.fire_shield_sms_ms.adapter.in.web.dto.ContatoRequest;
import br.com.catech.fire_shield_sms_ms.adapter.in.web.dto.ContatoResponse;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Contato;
import br.com.catech.fire_shield_sms_ms.application.core.exceptions.DomainValidationException;

public final class ContatoWebMapper {

    private ContatoWebMapper() {
    }

    public static Contato toDomain(ContatoRequest request) {
        if (request == null) {
            throw new DomainValidationException("dados do contato sao obrigatorios");
        }

        return Contato.criarNovo(
                request.nome(),
                request.descricao(),
                request.numero(),
                request.estado()
        );
    }

    public static ContatoResponse toResponse(Contato contato) {
        return new ContatoResponse(
                contato.getUuid(),
                contato.getNome(),
                contato.getDescricao(),
                contato.getNumero(),
                contato.getEstado()
        );
    }
}
