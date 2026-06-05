package br.com.catech.fire_shield_sms_ms.framework.config.usecase;

import br.com.catech.fire_shield_sms_ms.application.ports.in.GerenciarContatoUseCase;
import br.com.catech.fire_shield_sms_ms.application.ports.out.ContatoRepository;
import br.com.catech.fire_shield_sms_ms.application.usecases.GerenciarContatoUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GerenciarContatoUseCaseConfig {

    @Bean
    GerenciarContatoUseCase gerenciarContatoUseCase(ContatoRepository contatoRepository) {
        return new GerenciarContatoUseCaseImpl(contatoRepository);
    }
}

