package br.com.catech.fire_shield_sms_ms.application.ports.in;

import br.com.catech.fire_shield_sms_ms.application.core.entities.Contato;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Ocorrencia;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Sms;

import java.util.List;

public interface McpConsultasUseCase {
    List<Ocorrencia> listOcorrencias();
    List<Contato> listContatos();
    Contato findFirstByEstado(String estado);
    List<Sms> listSms();
}
