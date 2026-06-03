package br.com.catech.fire_shield_sms_ms.adapter.in.messaging;

import br.com.catech.fire_shield_sms_ms.adapter.in.messaging.dto.OcorrenciaPayload;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Ocorrencia;
import br.com.catech.fire_shield_sms_ms.application.ports.out.OcorrenciaConsumer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Adapter que implementa o port de saída {@link OcorrenciaConsumer}.
 *
 * <p>Responsabilidade única: deserializar o JSON recebido do broker
 * em uma entidade de domínio {@link Ocorrencia} validada.
 * Não possui {@code @RabbitListener} — o disparo fica em {@code OcorrenciaListenerAdapter}.
 */
@Slf4j
@Component
public class OcorrenciaConsumerAdapter implements OcorrenciaConsumer {

    private final ObjectMapper objectMapper;

    public OcorrenciaConsumerAdapter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Ocorrencia consumirOcorrencia(String mensagem) {
        try {

            OcorrenciaPayload payload = objectMapper.readValue(mensagem, OcorrenciaPayload.class);

            log.info("[CONSUMER] Payload desserializado: severidade={}, estado={}, lat={}, lon={}",
                    payload.severidade(), payload.estado(),
                    payload.latitude(), payload.longitude());

            return mapearParaDominio(payload);

        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "[CONSUMER] Falha ao deserializar mensagem de ocorrência: " + e.getMessage(), e);
        }
    }

    // -------------------------------------------------------------------------
    // Mapeamento Payload → Domínio
    // -------------------------------------------------------------------------

    private static Ocorrencia mapearParaDominio(OcorrenciaPayload payload) {
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
