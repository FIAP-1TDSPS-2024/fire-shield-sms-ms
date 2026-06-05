package br.com.catech.fire_shield_sms_ms.adapter.out.persistence;

import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.mapper.OcorrenciaPersistenceMapper;
import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.mapper.SmsPersistenceMapper;
import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.repository.OcorrenciaJpaRepository;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Ocorrencia;
import br.com.catech.fire_shield_sms_ms.application.ports.out.OcorrenciaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

public class OcorrenciaJpaAdapter implements OcorrenciaRepository {

    private final OcorrenciaJpaRepository repository;

    public OcorrenciaJpaAdapter(OcorrenciaJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Ocorrencia ocorrencia) {
        repository.save(OcorrenciaPersistenceMapper.toEntity(ocorrencia));
    }

    @Override
    public List<Ocorrencia> listAll() {
        return repository.findAll()
                .stream()
                .map(OcorrenciaPersistenceMapper::toDomain)
                .toList();
    }
}

