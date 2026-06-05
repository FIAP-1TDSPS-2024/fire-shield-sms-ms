package br.com.catech.fire_shield_sms_ms.framework.config.usecase;

import br.com.catech.fire_shield_sms_ms.application.ports.in.ProcessarOcorrenciaUseCase;
import br.com.catech.fire_shield_sms_ms.application.ports.out.ContatoRepository;
import br.com.catech.fire_shield_sms_ms.application.ports.out.OcorrenciaRepository;
import br.com.catech.fire_shield_sms_ms.application.ports.out.SmsPortOut;
import br.com.catech.fire_shield_sms_ms.application.ports.out.SmsRepository;
import br.com.catech.fire_shield_sms_ms.application.usecases.ProcessarOcorrenciaUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProcessarOcorrenciaUseCaseConfig {

    @Bean
    ProcessarOcorrenciaUseCase processarOcorrenciaUseCase(
            OcorrenciaRepository ocorrenciaRepository,
            ContatoRepository contatoRepository,
            SmsRepository smsRepository,
            SmsPortOut smsPortOut){

        return new ProcessarOcorrenciaUseCaseImpl(
                ocorrenciaRepository,
                contatoRepository,
                smsRepository,
                smsPortOut);
    }
}
