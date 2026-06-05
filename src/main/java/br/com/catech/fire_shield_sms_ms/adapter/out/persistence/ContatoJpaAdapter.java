package br.com.catech.fire_shield_sms_ms.adapter.out.persistence;

import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.mapper.ContatoPersistenceMapper;
import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.repository.ContatoJpaRepository;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Contato;
import br.com.catech.fire_shield_sms_ms.application.ports.out.ContatoRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public Contato save(Contato contato) {
        return ContatoPersistenceMapper.toDomain(
                repository.save(ContatoPersistenceMapper.toEntity(contato))
        );
    }

    @Override
    public List<Contato> findAll() {
        return repository.findAll()
                .stream()
                .map(ContatoPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Contato> findByUuid(UUID uuid) {
        return repository.findById(uuid)
                .map(ContatoPersistenceMapper::toDomain);
    }

    @Override
    public boolean existsByUuid(UUID uuid) {
        return repository.existsById(uuid);
    }

    @Override
    public void deleteByUuid(UUID uuid) {
        repository.deleteById(uuid);
    }
}
