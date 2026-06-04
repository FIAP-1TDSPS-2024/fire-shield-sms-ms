package br.com.catech.fire_shield_sms_ms.application.usecases;

import br.com.catech.fire_shield_sms_ms.application.core.entities.Contato;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Ocorrencia;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Sms;
import br.com.catech.fire_shield_sms_ms.application.core.exceptions.EnvioSmsException;
import br.com.catech.fire_shield_sms_ms.application.core.exceptions.NotFoundException;
import br.com.catech.fire_shield_sms_ms.application.ports.in.ProcessarOcorrenciaUseCase;
import br.com.catech.fire_shield_sms_ms.application.ports.out.ContatoRepository;
import br.com.catech.fire_shield_sms_ms.application.ports.out.OcorrenciaRepository;
import br.com.catech.fire_shield_sms_ms.application.ports.out.SmsPortOut;
import br.com.catech.fire_shield_sms_ms.application.ports.out.SmsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProcessarOcorrenciaUseCaseImpl implements ProcessarOcorrenciaUseCase {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final ContatoRepository contatoRepository;
    private final SmsRepository smsRepository;
    private final SmsPortOut smsPortOut;

    public ProcessarOcorrenciaUseCaseImpl(OcorrenciaRepository ocorrenciaRepository, ContatoRepository contatoRepository, SmsRepository smsRepository, SmsPortOut smsPortOut) {
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.contatoRepository = contatoRepository;
        this.smsRepository = smsRepository;
        this.smsPortOut = smsPortOut;
    }

    @Override
    public void processar(Ocorrencia ocorrencia) {
        log.info("[USECASE] Processando ocorrência: uuid={}, severidade={}, estado={}",
                ocorrencia.getUuid(), ocorrencia.getSeveridade(), ocorrencia.getEstado());

        ocorrenciaRepository.save(ocorrencia);

        log.info("[USECASE] Ocorrência persistida com sucesso: uuid={}", ocorrencia.getUuid());

        Contato contato = this.contatoRepository.findFirstByEstado(ocorrencia.getEstado());

        if (contato == null) {
            throw new NotFoundException(
                    "Nenhum contato encontrado para o estado "
                            + ocorrencia.getEstado()
            );
        }

        log.info("[USECASE] De acordo com a Ocorrencia {} foi consultado o Órgão responsável: uuid={}, nome={}, estado={}",
                ocorrencia.getUuid(), contato.getUuid(), contato.getNome(), contato.getEstado());

        Sms sms = Sms.criarNovo(contato.getNumero(), Sms.criarMensagem(ocorrencia, contato),  ocorrencia, contato);

        smsRepository.save(sms);
        log.info(
                "[USECASE] SMS criado: uuid={}, destino={}",
                sms.getUuid(),
                sms.getNumeroDestino()
        );

        log.info(
                "[USECASE] Enviando SMS: uuid={}, destino={}",
                sms.getUuid(),
                sms.getNumeroDestino()
        );

        try {

            smsPortOut.enviarSms(sms);

            log.info(
                    "[USECASE] SMS enviado com sucesso: uuid={}, destino={}",
                    sms.getUuid(),
                    sms.getNumeroDestino()
            );

        } catch (Exception e) {
            log.error(
                    "[USECASE] Falha ao enviar SMS: uuid={}",
                    sms.getUuid(),
                    e
            );
        }
    }
}
