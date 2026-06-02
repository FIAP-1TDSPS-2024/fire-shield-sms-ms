package br.com.catech.fire_shield_sms_ms.adapter.out.persistence.mapper;

import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.entities.SmsJpaEntity;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Sms;

public final class SmsPersistenceMapper {

    private SmsPersistenceMapper() {
    }

    public static Sms toDomain(SmsJpaEntity entity) {
        return Sms.reconstituir(
                entity.getUuid(),
                entity.getNumeroOrigem(),
                entity.getNumeroDestino(),
                entity.getMensagem(),
                entity.getDataEnvio()
        );
    }

    public static SmsJpaEntity toEntity(Sms sms) {
        SmsJpaEntity entity = new SmsJpaEntity();
        entity.setUuid(sms.getUuid());
        entity.setNumeroOrigem(sms.getNumeroOrigem());
        entity.setNumeroDestino(sms.getNumeroDestino());
        entity.setMensagem(sms.getMensagem());
        entity.setDataEnvio(sms.getDataEnvio());
        return entity;
    }
}

