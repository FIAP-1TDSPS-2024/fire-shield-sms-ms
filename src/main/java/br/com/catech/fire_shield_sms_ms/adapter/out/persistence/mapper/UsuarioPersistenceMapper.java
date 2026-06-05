package br.com.catech.fire_shield_sms_ms.adapter.out.persistence.mapper;

import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.entities.UsuarioJpaEntity;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Usuario;

public final class UsuarioPersistenceMapper {

    private UsuarioPersistenceMapper() {
    }

    public static Usuario toDomain(UsuarioJpaEntity entity) {
        if (entity == null) {
            return null;
        }

        return Usuario.reconstituir(
                entity.getUuid(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getNome(),
                Boolean.TRUE.equals(entity.getAtivo())
        );
    }
}

