package br.com.catech.fire_shield_sms_ms.framework.config.usecase;

import br.com.catech.fire_shield_sms_ms.application.ports.in.McpConsultasUseCase;
import br.com.catech.fire_shield_sms_ms.application.ports.out.ContatoRepository;
import br.com.catech.fire_shield_sms_ms.application.ports.out.OcorrenciaRepository;
import br.com.catech.fire_shield_sms_ms.application.ports.out.SmsPortOut;
import br.com.catech.fire_shield_sms_ms.application.ports.out.SmsRepository;
import br.com.catech.fire_shield_sms_ms.application.usecases.McpConsultasUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpConsultasUseCaseConfig {

    @Bean
    public McpConsultasUseCase mcpConsultasUseCase(OcorrenciaRepository ocorrenciaRepository, ContatoRepository contatoRepository, SmsRepository smsRepository) {
        return new McpConsultasUseCaseImpl(ocorrenciaRepository, contatoRepository, smsRepository);
    }
}
