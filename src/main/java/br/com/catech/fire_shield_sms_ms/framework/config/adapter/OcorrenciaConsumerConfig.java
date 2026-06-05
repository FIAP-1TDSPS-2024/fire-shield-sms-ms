package br.com.catech.fire_shield_sms_ms.framework.config.adapter;

import br.com.catech.fire_shield_sms_ms.adapter.in.messaging.OcorrenciaConsumerAdapter;
import br.com.catech.fire_shield_sms_ms.application.ports.out.OcorrenciaConsumer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OcorrenciaConsumerConfig {

    @Bean
    OcorrenciaConsumer ocorrenciaConsumer(ObjectMapper objectMapper){
        return new OcorrenciaConsumerAdapter(objectMapper);
    }
}
