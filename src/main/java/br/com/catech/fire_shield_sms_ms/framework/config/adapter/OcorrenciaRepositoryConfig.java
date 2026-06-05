package br.com.catech.fire_shield_sms_ms.framework.config.adapter;

import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.OcorrenciaJpaAdapter;
import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.repository.OcorrenciaJpaRepository;
import br.com.catech.fire_shield_sms_ms.application.ports.out.OcorrenciaRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OcorrenciaRepositoryConfig {

    @Bean
    OcorrenciaRepository ocorrenciaRepository(OcorrenciaJpaRepository ocorrenciaJpaRepository){
        return new OcorrenciaJpaAdapter(ocorrenciaJpaRepository);
    }
}
