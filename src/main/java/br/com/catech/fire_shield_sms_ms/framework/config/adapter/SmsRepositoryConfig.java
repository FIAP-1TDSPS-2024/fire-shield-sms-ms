package br.com.catech.fire_shield_sms_ms.framework.config.adapter;

import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.SmsJpaAdapter;
import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.repository.SmsJpaRepository;
import br.com.catech.fire_shield_sms_ms.application.ports.out.SmsRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsRepositoryConfig {

    @Bean
    public SmsRepository smsRepository(SmsJpaRepository smsJpaRepository) {
        return new SmsJpaAdapter(smsJpaRepository);
    }
}
