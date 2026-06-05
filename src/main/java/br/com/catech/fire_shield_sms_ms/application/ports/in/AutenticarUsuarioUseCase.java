package br.com.catech.fire_shield_sms_ms.application.ports.in;

import br.com.catech.fire_shield_sms_ms.application.core.entities.TokenAcesso;

public interface AutenticarUsuarioUseCase {
    TokenAcesso autenticar(String username, String password);
}

