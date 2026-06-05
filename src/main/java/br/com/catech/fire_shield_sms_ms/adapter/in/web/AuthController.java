package br.com.catech.fire_shield_sms_ms.adapter.in.web;

import br.com.catech.fire_shield_sms_ms.adapter.in.web.dto.LoginRequest;
import br.com.catech.fire_shield_sms_ms.adapter.in.web.dto.LoginResponse;
import br.com.catech.fire_shield_sms_ms.adapter.in.web.mapper.AuthWebMapper;
import br.com.catech.fire_shield_sms_ms.application.core.entities.TokenAcesso;
import br.com.catech.fire_shield_sms_ms.application.core.exceptions.DomainValidationException;
import br.com.catech.fire_shield_sms_ms.application.ports.in.AutenticarUsuarioUseCase;

public class AuthController {

    private final AutenticarUsuarioUseCase autenticarUsuarioUseCase;

    public AuthController(AutenticarUsuarioUseCase autenticarUsuarioUseCase) {
        this.autenticarUsuarioUseCase = autenticarUsuarioUseCase;
    }

    public LoginResponse login(LoginRequest request) {
        if (request == null) {
            throw new DomainValidationException("dados de login sao obrigatorios");
        }

        TokenAcesso tokenAcesso = autenticarUsuarioUseCase.autenticar(
                request.username(),
                request.password()
        );

        return AuthWebMapper.toResponse(tokenAcesso);
    }
}
