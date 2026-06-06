package br.com.catech.fire_shield_sms_ms.application.usecases;

import br.com.catech.fire_shield_sms_ms.TestFixtures;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Contato;
import br.com.catech.fire_shield_sms_ms.application.core.exceptions.NotFoundException;
import br.com.catech.fire_shield_sms_ms.application.ports.out.ContatoRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GerenciarContatoUseCaseImplTest {

    private final ContatoRepository contatoRepository = mock(ContatoRepository.class);
    private final GerenciarContatoUseCaseImpl useCase = new GerenciarContatoUseCaseImpl(contatoRepository);

    @Test
    void deveCriarContato() {
        Contato contato = TestFixtures.contato();
        when(contatoRepository.save(contato)).thenReturn(contato);

        Contato resultado = useCase.criar(contato);

        assertThat(resultado).isSameAs(contato);
        verify(contatoRepository).save(contato);
    }

    @Test
    void deveListarContatos() {
        Contato contato = TestFixtures.contato();
        when(contatoRepository.findAll()).thenReturn(List.of(contato));

        assertThat(useCase.listar()).containsExactly(contato);
    }

    @Test
    void deveBuscarContatoPorUuid() {
        Contato contato = TestFixtures.contato();
        when(contatoRepository.findByUuid(contato.getUuid())).thenReturn(Optional.of(contato));

        assertThat(useCase.buscarPorUuid(contato.getUuid())).isSameAs(contato);
    }

    @Test
    void deveFalharAoBuscarContatoInexistente() {
        UUID uuid = UUID.randomUUID();
        when(contatoRepository.findByUuid(uuid)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.buscarPorUuid(uuid))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Contato nao encontrado");
    }

    @Test
    void deveAtualizarContatoExistentePreservandoUuid() {
        UUID uuid = UUID.randomUUID();
        Contato contato = TestFixtures.contato();
        when(contatoRepository.existsByUuid(uuid)).thenReturn(true);
        when(contatoRepository.save(org.mockito.ArgumentMatchers.any(Contato.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Contato resultado = useCase.atualizar(uuid, contato);

        assertThat(resultado.getUuid()).isEqualTo(uuid);
        assertThat(resultado.getNome()).isEqualTo(contato.getNome());
        verify(contatoRepository).save(org.mockito.ArgumentMatchers.any(Contato.class));
    }

    @Test
    void deveExcluirContatoExistente() {
        UUID uuid = UUID.randomUUID();
        when(contatoRepository.existsByUuid(uuid)).thenReturn(true);

        useCase.excluir(uuid);

        verify(contatoRepository).deleteByUuid(uuid);
    }
}

