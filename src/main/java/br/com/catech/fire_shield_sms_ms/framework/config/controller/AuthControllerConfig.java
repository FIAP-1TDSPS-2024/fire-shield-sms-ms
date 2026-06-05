package br.com.catech.fire_shield_sms_ms.framework.config.controller;

import br.com.catech.fire_shield_sms_ms.adapter.in.web.AuthController;
import br.com.catech.fire_shield_sms_ms.application.ports.in.AutenticarUsuarioUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AuthControllerConfig {

    @Bean
    AuthController authController(AutenticarUsuarioUseCase autenticarUsuarioUseCase) {
        return new AuthController(autenticarUsuarioUseCase);
    }
}

