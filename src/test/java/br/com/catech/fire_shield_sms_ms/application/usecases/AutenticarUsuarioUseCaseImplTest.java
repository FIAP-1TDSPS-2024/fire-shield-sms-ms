package br.com.catech.fire_shield_sms_ms.application.usecases;

import br.com.catech.fire_shield_sms_ms.TestFixtures;
import br.com.catech.fire_shield_sms_ms.application.core.entities.TokenAcesso;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Usuario;
import br.com.catech.fire_shield_sms_ms.application.core.exceptions.AuthenticationFailedException;
import br.com.catech.fire_shield_sms_ms.application.core.exceptions.DomainValidationException;
import br.com.catech.fire_shield_sms_ms.application.ports.out.SenhaEncoder;
import br.com.catech.fire_shield_sms_ms.application.ports.out.TokenProvider;
import br.com.catech.fire_shield_sms_ms.application.ports.out.UsuarioRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AutenticarUsuarioUseCaseImplTest {

    private final UsuarioRepository usuarioRepository = mock(UsuarioRepository.class);
    private final SenhaEncoder senhaEncoder = mock(SenhaEncoder.class);
    private final TokenProvider tokenProvider = mock(TokenProvider.class);
    private final AutenticarUsuarioUseCaseImpl useCase =
            new AutenticarUsuarioUseCaseImpl(usuarioRepository, senhaEncoder, tokenProvider);

    @Test
    void deveAutenticarUsuarioAtivoComSenhaValida() {
        Usuario usuario = TestFixtures.usuarioAtivo();
        TokenAcesso token = TestFixtures.token();

        when(usuarioRepository.findByUsername("admin")).thenReturn(Optional.of(usuario));
        when(senhaEncoder.matches("admin123", usuario.getPassword())).thenReturn(true);
        when(tokenProvider.gerarToken(usuario)).thenReturn(token);

        TokenAcesso resultado = useCase.autenticar(" admin ", "admin123");

        assertThat(resultado.token()).isEqualTo("jwt-token");
        verify(tokenProvider).gerarToken(usuario);
    }

    @Test
    void deveFalharQuandoUsuarioNaoExiste() {
        when(usuarioRepository.findByUsername("admin")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.autenticar("admin", "admin123"))
                .isInstanceOf(AuthenticationFailedException.class)
                .hasMessage("credenciais invalidas");
    }

    @Test
    void deveFalharQuandoSenhaNaoConfere() {
        Usuario usuario = TestFixtures.usuarioAtivo();

        when(usuarioRepository.findByUsername("admin")).thenReturn(Optional.of(usuario));
        when(senhaEncoder.matches("errada", usuario.getPassword())).thenReturn(false);

        assertThatThrownBy(() -> useCase.autenticar("admin", "errada"))
                .isInstanceOf(AuthenticationFailedException.class)
                .hasMessage("credenciais invalidas");
    }

    @Test
    void deveValidarCamposObrigatorios() {
        assertThatThrownBy(() -> useCase.autenticar(" ", "admin123"))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage("username e password sao obrigatorios");
    }
}

