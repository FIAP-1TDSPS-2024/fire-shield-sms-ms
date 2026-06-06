package br.com.catech.fire_shield_sms_ms.application.usecases;

import br.com.catech.fire_shield_sms_ms.TestFixtures;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Contato;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Ocorrencia;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Sms;
import br.com.catech.fire_shield_sms_ms.application.core.exceptions.NotFoundException;
import br.com.catech.fire_shield_sms_ms.application.ports.out.ContatoRepository;
import br.com.catech.fire_shield_sms_ms.application.ports.out.OcorrenciaRepository;
import br.com.catech.fire_shield_sms_ms.application.ports.out.SmsPortOut;
import br.com.catech.fire_shield_sms_ms.application.ports.out.SmsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProcessarOcorrenciaUseCaseImplTest {

    private final OcorrenciaRepository ocorrenciaRepository = mock(OcorrenciaRepository.class);
    private final ContatoRepository contatoRepository = mock(ContatoRepository.class);
    private final SmsRepository smsRepository = mock(SmsRepository.class);
    private final SmsPortOut smsPortOut = mock(SmsPortOut.class);
    private final ProcessarOcorrenciaUseCaseImpl useCase =
            new ProcessarOcorrenciaUseCaseImpl(ocorrenciaRepository, contatoRepository, smsRepository, smsPortOut);

    @Test
    void devePersistirOcorrenciaCriarSmsEEnviarQuandoContatoExiste() {
        Ocorrencia ocorrencia = TestFixtures.ocorrencia();
        Contato contato = TestFixtures.contato();
        when(contatoRepository.findFirstByEstado(ocorrencia.getEstado())).thenReturn(contato);

        useCase.processar(ocorrencia);

        verify(ocorrenciaRepository).save(ocorrencia);

        ArgumentCaptor<Sms> smsCaptor = ArgumentCaptor.forClass(Sms.class);
        verify(smsRepository).save(smsCaptor.capture());
        verify(smsPortOut).enviarSms(smsCaptor.getValue());

        assertThat(smsCaptor.getValue().getNumeroDestino()).isEqualTo(contato.getNumero());
        assertThat(smsCaptor.getValue().getMensagem()).contains("ALERTA DE INCENDIO");
    }

    @Test
    void deveFalharQuandoNaoExisteContatoParaEstado() {
        Ocorrencia ocorrencia = TestFixtures.ocorrencia();
        when(contatoRepository.findFirstByEstado(ocorrencia.getEstado())).thenReturn(null);

        assertThatThrownBy(() -> useCase.processar(ocorrencia))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Nenhum contato encontrado");

        verify(ocorrenciaRepository).save(ocorrencia);
    }
}

