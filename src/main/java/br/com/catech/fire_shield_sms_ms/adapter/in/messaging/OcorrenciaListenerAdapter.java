package br.com.catech.fire_shield_sms_ms.adapter.in.messaging;

import br.com.catech.fire_shield_sms_ms.adapter.in.messaging.dto.OcorrenciaPayload;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Ocorrencia;
import br.com.catech.fire_shield_sms_ms.application.ports.in.ProcessarOcorrenciaUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Adapter de entrada (messaging).
 *
 * <p>Responsabilidades:
 * <ol>
 *   <li>Receber a mensagem bruta do RabbitMQ</li>
 *   <li>Traduzir o payload JSON para a entidade de domínio {@link Ocorrencia}</li>
 *   <li>Delegar ao UseCase — sem nenhuma regra de negócio aqui</li>
 * </ol>
 *
 * <p>A deserialização é responsabilidade do adapter de entrada, não do UseCase.
 * O UseCase não deve saber que existe JSON, RabbitMQ ou qualquer protocolo de transporte.
 */
@Slf4j
@Component
public class OcorrenciaListenerAdapter {

    private final ProcessarOcorrenciaUseCase processarOcorrenciaUseCase;
    private final ObjectMapper objectMapper;

    public OcorrenciaListenerAdapter(ProcessarOcorrenciaUseCase processarOcorrenciaUseCase,
                                     ObjectMapper objectMapper) {
        this.processarOcorrenciaUseCase = processarOcorrenciaUseCase;
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "${RABBITMQ_QUEUE_ORIGIN}")
    public void onMessage(String mensagem) {
        log.info("[RABBITMQ] Mensagem recebida na fila");
        try {
            Ocorrencia ocorrencia = deserializar(mensagem);
            processarOcorrenciaUseCase.processar(ocorrencia);
        } catch (Exception e) {
            log.error("[RABBITMQ] Falha ao processar mensagem: {}", e.getMessage(), e);
        }
    }

    // -------------------------------------------------------------------------
    // Tradução: formato de transporte → entidade de domínio
    // -------------------------------------------------------------------------

    private Ocorrencia deserializar(String mensagem) throws Exception {
        OcorrenciaPayload payload = objectMapper.readValue(mensagem, OcorrenciaPayload.class);

        System.out.println(mensagem);

        log.info("[RABBITMQ] Payload desserializado: severidade={}, estado={}, lat={}, lon={}",
                payload.severidade(), payload.estado(),
                payload.latitude(), payload.longitude());

        return Ocorrencia.criarNova(
                payload.latitude(),
                payload.longitude(),
                payload.severidade(),
                payload.horarioDeteccao(),
                payload.nome(),
                payload.bairro(),
                payload.cidade(),
                payload.estado(),
                payload.cep()
        );
    }
}
