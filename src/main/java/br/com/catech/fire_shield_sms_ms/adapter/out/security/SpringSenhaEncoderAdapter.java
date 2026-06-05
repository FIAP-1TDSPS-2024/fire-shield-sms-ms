package br.com.catech.fire_shield_sms_ms.adapter.out.security;

import br.com.catech.fire_shield_sms_ms.application.ports.out.SenhaEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class SpringSenhaEncoderAdapter implements SenhaEncoder {

    private final PasswordEncoder passwordEncoder;

    public SpringSenhaEncoderAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}

