package br.com.catech.fire_shield_sms_ms.adapter.out.persistence.repository;

import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.entities.OcorrenciaJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OcorrenciaJpaRepository extends JpaRepository<OcorrenciaJpaEntity, UUID> {
}

