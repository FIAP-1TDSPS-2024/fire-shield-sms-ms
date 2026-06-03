package br.com.catech.fire_shield_sms_ms.application.usecases;

import br.com.catech.fire_shield_sms_ms.application.core.entities.Ocorrencia;
import br.com.catech.fire_shield_sms_ms.application.ports.in.ProcessarOcorrenciaUseCase;
import br.com.catech.fire_shield_sms_ms.application.ports.out.OcorrenciaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProcessarOcorrenciaUseCaseImpl implements ProcessarOcorrenciaUseCase {

    private final OcorrenciaRepository ocorrenciaRepository;

    public ProcessarOcorrenciaUseCaseImpl(OcorrenciaRepository ocorrenciaRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
    }

    @Override
    public void processar(Ocorrencia ocorrencia) {
        log.info("[USECASE] Processando ocorrência: uuid={}, severidade={}, estado={}",
                ocorrencia.getUuid(), ocorrencia.getSeveridade(), ocorrencia.getEstado());

        ocorrenciaRepository.save(ocorrencia);

        log.info("[USECASE] Ocorrência persistida com sucesso: uuid={}", ocorrencia.getUuid());
    }
}
