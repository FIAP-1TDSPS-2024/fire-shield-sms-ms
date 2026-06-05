package br.com.catech.fire_shield_sms_ms.framework.config.adapter;

import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.UsuarioJpaAdapter;
import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.repository.UsuarioJpaRepository;
import br.com.catech.fire_shield_sms_ms.application.ports.out.UsuarioRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UsuarioRepositoryConfig {

    @Bean
    UsuarioRepository usuarioRepository(UsuarioJpaRepository usuarioJpaRepository) {
        return new UsuarioJpaAdapter(usuarioJpaRepository);
    }
}

