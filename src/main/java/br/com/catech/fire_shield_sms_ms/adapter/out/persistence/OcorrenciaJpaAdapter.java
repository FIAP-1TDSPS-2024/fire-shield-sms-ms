package br.com.catech.fire_shield_sms_ms.adapter.out.persistence;

import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.mapper.OcorrenciaPersistenceMapper;
import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.repository.OcorrenciaJpaRepository;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Ocorrencia;
import br.com.catech.fire_shield_sms_ms.application.ports.out.OcorrenciaRepository;
import org.springframework.stereotype.Component;

public class OcorrenciaJpaAdapter implements OcorrenciaRepository {

    private final OcorrenciaJpaRepository repository;

    public OcorrenciaJpaAdapter(OcorrenciaJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Ocorrencia ocorrencia) {
        repository.save(OcorrenciaPersistenceMapper.toEntity(ocorrencia));
    }
}

