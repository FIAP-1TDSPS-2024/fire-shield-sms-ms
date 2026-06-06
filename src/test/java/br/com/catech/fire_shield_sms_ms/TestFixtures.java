package br.com.catech.fire_shield_sms_ms;

import br.com.catech.fire_shield_sms_ms.application.core.entities.Contato;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Ocorrencia;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Sms;
import br.com.catech.fire_shield_sms_ms.application.core.entities.TokenAcesso;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Usuario;
import br.com.catech.fire_shield_sms_ms.application.core.enums.SeveridadeOcorrenciaEnum;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public final class TestFixtures {

    private TestFixtures() {
    }

    public static Contato contato() {
        return Contato.reconstituir(
                UUID.fromString("00000000-0000-0000-0000-000000000001"),
                "Defesa Civil SP",
                "Contato de teste",
                "+5511999999999",
                "Sao Paulo"
        );
    }

    public static Ocorrencia ocorrencia() {
        return Ocorrencia.reconstituir(
                UUID.fromString("00000000-0000-0000-0000-000000000002"),
                -23.5505,
                -46.6333,
                SeveridadeOcorrenciaEnum.ALTA,
                LocalDateTime.now().minusMinutes(5),
                "Avenida Paulista",
                "Bela Vista",
                "Sao Paulo",
                "Sao Paulo",
                "01310-100"
        );
    }

    public static Sms sms() {
        Contato contato = contato();
        Ocorrencia ocorrencia = ocorrencia();
        return Sms.reconstituir(
                UUID.fromString("00000000-0000-0000-0000-000000000003"),
                contato.getNumero(),
                Sms.criarMensagem(ocorrencia, contato),
                LocalDateTime.now().minusMinutes(1),
                ocorrencia,
                contato
        );
    }

    public static Usuario usuarioAtivo() {
        return Usuario.reconstituir(
                UUID.fromString("00000000-0000-0000-0000-000000000004"),
                "admin",
                "$2a$12$hash",
                "Administrador",
                true
        );
    }

    public static TokenAcesso token() {
        return new TokenAcesso("jwt-token", Instant.now().plusSeconds(3600));
    }
}

