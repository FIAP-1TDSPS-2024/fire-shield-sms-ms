package br.com.catech.fire_shield_sms_ms.application.ports.out;

import br.com.catech.fire_shield_sms_ms.application.core.entities.Contato;

import java.util.List;

public interface ContatoRepository {
    Contato findContatoByUf(String estadoSigla);
}

