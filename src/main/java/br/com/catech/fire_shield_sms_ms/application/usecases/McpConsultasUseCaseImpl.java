package br.com.catech.fire_shield_sms_ms.application.usecases;

import br.com.catech.fire_shield_sms_ms.application.core.entities.Contato;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Ocorrencia;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Sms;
import br.com.catech.fire_shield_sms_ms.application.ports.in.McpConsultasUseCase;
import br.com.catech.fire_shield_sms_ms.application.ports.out.ContatoRepository;
import br.com.catech.fire_shield_sms_ms.application.ports.out.OcorrenciaRepository;
import br.com.catech.fire_shield_sms_ms.application.ports.out.SmsRepository;
import org.springaicommunity.mcp.annotation.McpTool;

import java.util.List;

public class McpConsultasUseCaseImpl implements McpConsultasUseCase {

    private final OcorrenciaRepository ocorrenciaRepository;
    private final ContatoRepository contatoRepository;
    private final SmsRepository smsRepository;

    public McpConsultasUseCaseImpl(OcorrenciaRepository ocorrenciaRepository, ContatoRepository contatoRepository, SmsRepository smsRepository) {
        this.ocorrenciaRepository = ocorrenciaRepository;
        this.contatoRepository = contatoRepository;
        this.smsRepository = smsRepository;
    }


    @Override
    @McpTool(name = "list-events", description = "I will return all fire events")
    public List<Ocorrencia> listOcorrencias() {
        return ocorrenciaRepository.listAll();
    }

    @Override
    @McpTool(name = "list-contacts", description = "I will return all fire containment responsible agencies contacts")
    public List<Contato> listContatos() {
        return contatoRepository.findAll();
    }

    @Override
    @McpTool(name = "find-contact-by-state", description = "I will return the first fire containment responsible agency associated with the brazillian state informed")
    public Contato findFirstByEstado(String estado) {
        return contatoRepository.findFirstByEstado(estado);
    }

    @Override
    @McpTool(name = "list-sms", description = "I will return all sms messages sent")
    public List<Sms> listSms() {
        return smsRepository.listAll();
    }
}
