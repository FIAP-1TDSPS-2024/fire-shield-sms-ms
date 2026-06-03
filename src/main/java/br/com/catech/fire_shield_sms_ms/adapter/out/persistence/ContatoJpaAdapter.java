package br.com.catech.fire_shield_sms_ms.adapter.out.persistence;

import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.mapper.ContatoPersistenceMapper;
import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.repository.ContatoJpaRepository;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Contato;
import br.com.catech.fire_shield_sms_ms.application.ports.out.ContatoRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContatoJpaAdapter implements ContatoRepository {

    private final ContatoJpaRepository repository;

    public ContatoJpaAdapter(ContatoJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Contato findFirstByEstado(String estado) {
        return ContatoPersistenceMapper
                .toDomain(repository.findFirstByEstado(estado));
    }
}

