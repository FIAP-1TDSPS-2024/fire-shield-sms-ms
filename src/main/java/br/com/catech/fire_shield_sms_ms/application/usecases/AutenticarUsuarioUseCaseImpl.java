package br.com.catech.fire_shield_sms_ms.application.usecases;

import br.com.catech.fire_shield_sms_ms.application.core.entities.TokenAcesso;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Usuario;
import br.com.catech.fire_shield_sms_ms.application.core.exceptions.AuthenticationFailedException;
import br.com.catech.fire_shield_sms_ms.application.core.exceptions.DomainValidationException;
import br.com.catech.fire_shield_sms_ms.application.ports.in.AutenticarUsuarioUseCase;
import br.com.catech.fire_shield_sms_ms.application.ports.out.SenhaEncoder;
import br.com.catech.fire_shield_sms_ms.application.ports.out.TokenProvider;
import br.com.catech.fire_shield_sms_ms.application.ports.out.UsuarioRepository;

public class AutenticarUsuarioUseCaseImpl implements AutenticarUsuarioUseCase {

    private final UsuarioRepository usuarioRepository;
    private final SenhaEncoder senhaEncoder;
    private final TokenProvider tokenProvider;

    public AutenticarUsuarioUseCaseImpl(
            UsuarioRepository usuarioRepository,
            SenhaEncoder senhaEncoder,
            TokenProvider tokenProvider
    ) {
        this.usuarioRepository = usuarioRepository;
        this.senhaEncoder = senhaEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public TokenAcesso autenticar(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new DomainValidationException("username e password sao obrigatorios");
        }

        Usuario usuario = usuarioRepository.findByUsername(username.trim())
                .filter(Usuario::isAtivo)
                .filter(user -> senhaEncoder.matches(password, user.getPassword()))
                .orElseThrow(() -> new AuthenticationFailedException("credenciais invalidas"));

        return tokenProvider.gerarToken(usuario);
    }
}
