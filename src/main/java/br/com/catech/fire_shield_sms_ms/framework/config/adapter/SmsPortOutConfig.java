package br.com.catech.fire_shield_sms_ms.framework.config.adapter;

import br.com.catech.fire_shield_sms_ms.adapter.out.sms.SmsAdapterOut;
import br.com.catech.fire_shield_sms_ms.application.ports.out.SmsPortOut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsPortOutConfig {

    @Bean
    SmsPortOut smsPortOut(){
        return new SmsAdapterOut();
    }
}
