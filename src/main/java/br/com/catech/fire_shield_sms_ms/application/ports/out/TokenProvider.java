package br.com.catech.fire_shield_sms_ms.application.ports.out;

import br.com.catech.fire_shield_sms_ms.application.core.entities.TokenAcesso;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Usuario;

public interface TokenProvider {
    TokenAcesso gerarToken(Usuario usuario);
}

