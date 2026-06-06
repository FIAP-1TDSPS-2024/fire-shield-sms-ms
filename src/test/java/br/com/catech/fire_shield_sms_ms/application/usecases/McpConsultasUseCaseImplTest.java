package br.com.catech.fire_shield_sms_ms.application.usecases;

import br.com.catech.fire_shield_sms_ms.TestFixtures;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Contato;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Ocorrencia;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Sms;
import br.com.catech.fire_shield_sms_ms.application.ports.out.ContatoRepository;
import br.com.catech.fire_shield_sms_ms.application.ports.out.OcorrenciaRepository;
import br.com.catech.fire_shield_sms_ms.application.ports.out.SmsRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class McpConsultasUseCaseImplTest {

    private final OcorrenciaRepository ocorrenciaRepository = mock(OcorrenciaRepository.class);
    private final ContatoRepository contatoRepository = mock(ContatoRepository.class);
    private final SmsRepository smsRepository = mock(SmsRepository.class);
    private final McpConsultasUseCaseImpl useCase =
            new McpConsultasUseCaseImpl(ocorrenciaRepository, contatoRepository, smsRepository);

    @Test
    void deveDelegarConsultasParaRepositorios() {
        Ocorrencia ocorrencia = TestFixtures.ocorrencia();
        Contato contato = TestFixtures.contato();
        Sms sms = TestFixtures.sms();

        when(ocorrenciaRepository.listAll()).thenReturn(List.of(ocorrencia));
        when(contatoRepository.findAll()).thenReturn(List.of(contato));
        when(contatoRepository.findFirstByEstado("Sao Paulo")).thenReturn(contato);
        when(smsRepository.listAll()).thenReturn(List.of(sms));

        assertThat(useCase.listOcorrencias()).containsExactly(ocorrencia);
        assertThat(useCase.listContatos()).containsExactly(contato);
        assertThat(useCase.findFirstByEstado("Sao Paulo")).isSameAs(contato);
        assertThat(useCase.listSms()).containsExactly(sms);
    }
}

