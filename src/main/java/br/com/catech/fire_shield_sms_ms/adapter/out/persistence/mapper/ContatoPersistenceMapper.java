package br.com.catech.fire_shield_sms_ms.adapter.out.persistence.mapper;

import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.entities.ContatoJpaEntity;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Contato;

public final class ContatoPersistenceMapper {

    private ContatoPersistenceMapper() {
    }

    public static Contato toDomain(ContatoJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        return Contato.reconstituir(
                entity.getUuid(),
                entity.getNome(),
                entity.getDescricao(),
                entity.getNumero(),
                entity.getEstado()
        );
    }

    public static ContatoJpaEntity toEntity(Contato contato) {
        ContatoJpaEntity entity = new ContatoJpaEntity();
        entity.setUuid(contato.getUuid());
        entity.setNome(contato.getNome());
        entity.setDescricao(contato.getDescricao());
        entity.setNumero(contato.getNumero());
        entity.setEstado(contato.getEstado());
        return entity;
    }
}

