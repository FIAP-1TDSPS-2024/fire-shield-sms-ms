package br.com.catech.fire_shield_sms_ms.framework.config.adapter;

import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.ContatoJpaAdapter;
import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.repository.ContatoJpaRepository;
import br.com.catech.fire_shield_sms_ms.application.ports.out.ContatoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContatoRepositoryConfig {

    @Bean
    ContatoRepository contatoRepository(ContatoJpaRepository contatoJpaRepository){
        return new ContatoJpaAdapter(contatoJpaRepository);
    }
}
