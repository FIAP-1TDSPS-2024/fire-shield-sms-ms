package br.com.catech.fire_shield_sms_ms.application.ports.out;

import br.com.catech.fire_shield_sms_ms.application.core.entities.Contato;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContatoRepository {
    Contato findFirstByEstado(String estado);
    Contato save(Contato contato);
    List<Contato> findAll();
    Optional<Contato> findByUuid(UUID uuid);
    boolean existsByUuid(UUID uuid);
    void deleteByUuid(UUID uuid);
}
