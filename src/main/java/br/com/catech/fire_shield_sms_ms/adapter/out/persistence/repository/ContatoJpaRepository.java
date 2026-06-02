package br.com.catech.fire_shield_sms_ms.adapter.out.persistence.repository;

import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.entities.ContatoJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ContatoJpaRepository extends JpaRepository<ContatoJpaEntity, UUID> {

    ContatoJpaEntity findByEstadoSigla(String estadoSigla);
}