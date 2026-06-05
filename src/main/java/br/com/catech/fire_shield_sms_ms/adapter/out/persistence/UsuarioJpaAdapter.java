package br.com.catech.fire_shield_sms_ms.adapter.out.persistence;

import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.mapper.UsuarioPersistenceMapper;
import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.repository.UsuarioJpaRepository;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Usuario;
import br.com.catech.fire_shield_sms_ms.application.ports.out.UsuarioRepository;

import java.util.Optional;

public class UsuarioJpaAdapter implements UsuarioRepository {

    private final UsuarioJpaRepository repository;

    public UsuarioJpaAdapter(UsuarioJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Usuario> findByUsername(String username) {
        return repository.findByUsername(username)
                .map(UsuarioPersistenceMapper::toDomain);
    }
}

