package br.com.catech.fire_shield_sms_ms.adapter.out.security;

import br.com.catech.fire_shield_sms_ms.application.core.entities.TokenAcesso;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Usuario;
import br.com.catech.fire_shield_sms_ms.application.ports.out.TokenProvider;
import br.com.catech.fire_shield_sms_ms.framework.security.JwtUtil;

public class JwtTokenProviderAdapter implements TokenProvider {

    private final JwtUtil jwtUtil;

    public JwtTokenProviderAdapter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public TokenAcesso gerarToken(Usuario usuario) {
        return jwtUtil.gerarToken(usuario);
    }
}

