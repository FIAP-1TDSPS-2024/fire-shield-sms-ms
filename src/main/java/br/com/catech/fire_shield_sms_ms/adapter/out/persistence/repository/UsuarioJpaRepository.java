package br.com.catech.fire_shield_sms_ms.adapter.out.persistence.repository;

import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.entities.UsuarioJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioJpaEntity, UUID> {
    Optional<UsuarioJpaEntity> findByUsername(String username);
}

