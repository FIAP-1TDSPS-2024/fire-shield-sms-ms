package br.com.catech.fire_shield_sms_ms.application.ports.out;

import br.com.catech.fire_shield_sms_ms.application.core.entities.Ocorrencia;

/**
 * Port de saída: o UseCase chama este port para deserializar
 * uma mensagem bruta do broker em uma entidade de domínio {@link Ocorrencia}.
 *
 * <p>O UseCase não conhece RabbitMQ, JSON nem qualquer detalhe de transporte.
 * A implementação concreta fica no adapter de messaging.
 */
public interface OcorrenciaConsumer {

    Ocorrencia consumirOcorrencia(String mensagem);
}
