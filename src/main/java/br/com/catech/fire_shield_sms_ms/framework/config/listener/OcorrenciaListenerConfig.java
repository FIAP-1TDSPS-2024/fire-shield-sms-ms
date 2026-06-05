package br.com.catech.fire_shield_sms_ms.framework.config.listener;

import br.com.catech.fire_shield_sms_ms.adapter.in.messaging.OcorrenciaListener;
import br.com.catech.fire_shield_sms_ms.application.ports.in.ProcessarOcorrenciaUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OcorrenciaListenerConfig {

    @Bean
    OcorrenciaListener ocorrenciaListener(ProcessarOcorrenciaUseCase processarOcorrenciaUseCase, ObjectMapper objectMapper){
        return new OcorrenciaListener(processarOcorrenciaUseCase, objectMapper);
    }
}
