package br.com.catech.fire_shield_sms_ms.adapter.out.persistence.mapper;

import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.entities.OcorrenciaJpaEntity;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Ocorrencia;

public final class OcorrenciaPersistenceMapper {

    private OcorrenciaPersistenceMapper() {
    }

    public static Ocorrencia toDomain(OcorrenciaJpaEntity entity) {
        return Ocorrencia.reconstituir(
                entity.getUuid(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getSeveridade(),
                entity.getHorarioDeteccao(),
                entity.getNome(),
                entity.getBairro(),
                entity.getCidade(),
                entity.getEstadoSigla(),
                entity.getEstadoNome(),
                entity.getCep()
        );
    }

    public static OcorrenciaJpaEntity toEntity(Ocorrencia ocorrencia) {
        OcorrenciaJpaEntity entity = new OcorrenciaJpaEntity();
        entity.setUuid(ocorrencia.getUuid());
        entity.setLatitude(ocorrencia.getLatitude());
        entity.setLongitude(ocorrencia.getLongitude());
        entity.setSeveridade(ocorrencia.getSeveridade());
        entity.setHorarioDeteccao(ocorrencia.getHorarioDeteccao());
        entity.setNome(ocorrencia.getNome());
        entity.setBairro(ocorrencia.getBairro());
        entity.setCidade(ocorrencia.getCidade());
        entity.setEstadoSigla(ocorrencia.getEstadoSigla());
        entity.setEstadoNome(ocorrencia.getEstadoNome());
        entity.setCep(ocorrencia.getCep());
        return entity;
    }
}

