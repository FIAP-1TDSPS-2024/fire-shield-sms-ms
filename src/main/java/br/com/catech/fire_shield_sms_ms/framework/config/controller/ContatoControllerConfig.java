package br.com.catech.fire_shield_sms_ms.framework.config.controller;

import br.com.catech.fire_shield_sms_ms.adapter.in.web.ContatoController;
import br.com.catech.fire_shield_sms_ms.application.ports.in.GerenciarContatoUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContatoControllerConfig {

    @Bean
    ContatoController contatoController(GerenciarContatoUseCase gerenciarContatoUseCase) {
        return new ContatoController(gerenciarContatoUseCase);
    }
}

