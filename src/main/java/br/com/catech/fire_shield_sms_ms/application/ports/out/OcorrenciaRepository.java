package br.com.catech.fire_shield_sms_ms.application.ports.out;

import br.com.catech.fire_shield_sms_ms.application.core.entities.Ocorrencia;

import java.util.List;

public interface OcorrenciaRepository {

    void save(Ocorrencia ocorrencia);
}
