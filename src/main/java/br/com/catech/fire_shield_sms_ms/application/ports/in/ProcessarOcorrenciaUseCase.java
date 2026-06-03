package br.com.catech.fire_shield_sms_ms.application.ports.in;

import br.com.catech.fire_shield_sms_ms.application.core.entities.Ocorrencia;

/**
 * Port de entrada: ponto de entrada da lógica de negócio para
 * processamento de ocorrências recebidas via broker de mensagens.
 *
 * <p>Recebe a entidade de domínio já construída e validada —
 * o adapter de entrada é responsável pela tradução do formato de transporte.
 */
public interface ProcessarOcorrenciaUseCase {

    void processar(Ocorrencia ocorrencia);
}
