package br.com.catech.fire_shield_sms_ms.application.ports.out;

import br.com.catech.fire_shield_sms_ms.application.core.entities.Usuario;

import java.util.Optional;

public interface UsuarioRepository {
    Optional<Usuario> findByUsername(String username);
}

