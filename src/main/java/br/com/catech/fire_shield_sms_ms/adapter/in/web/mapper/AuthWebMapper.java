package br.com.catech.fire_shield_sms_ms.adapter.in.web.mapper;

import br.com.catech.fire_shield_sms_ms.adapter.in.web.dto.LoginResponse;
import br.com.catech.fire_shield_sms_ms.application.core.entities.TokenAcesso;

public final class AuthWebMapper {

    private AuthWebMapper() {
    }

    public static LoginResponse toResponse(TokenAcesso tokenAcesso) {
        return new LoginResponse(
                "Bearer",
                tokenAcesso.token(),
                tokenAcesso.expiresAt()
        );
    }
}

