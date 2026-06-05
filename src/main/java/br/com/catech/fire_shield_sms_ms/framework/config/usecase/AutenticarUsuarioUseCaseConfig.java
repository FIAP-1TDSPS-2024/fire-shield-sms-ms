package br.com.catech.fire_shield_sms_ms.framework.config.usecase;

import br.com.catech.fire_shield_sms_ms.application.ports.in.AutenticarUsuarioUseCase;
import br.com.catech.fire_shield_sms_ms.application.ports.out.SenhaEncoder;
import br.com.catech.fire_shield_sms_ms.application.ports.out.TokenProvider;
import br.com.catech.fire_shield_sms_ms.application.ports.out.UsuarioRepository;
import br.com.catech.fire_shield_sms_ms.application.usecases.AutenticarUsuarioUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AutenticarUsuarioUseCaseConfig {

    @Bean
    AutenticarUsuarioUseCase autenticarUsuarioUseCase(
            UsuarioRepository usuarioRepository,
            SenhaEncoder senhaEncoder,
            TokenProvider tokenProvider
    ) {
        return new AutenticarUsuarioUseCaseImpl(usuarioRepository, senhaEncoder, tokenProvider);
    }
}

