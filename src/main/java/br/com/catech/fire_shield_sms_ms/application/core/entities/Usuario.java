package br.com.catech.fire_shield_sms_ms.application.core.entities;

import br.com.catech.fire_shield_sms_ms.application.core.exceptions.DomainValidationException;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

@Getter
public class Usuario {

    private static final int MAX_USERNAME_LENGTH = 80;
    private static final int MAX_PASSWORD_LENGTH = 255;
    private static final int MAX_NOME_LENGTH = 120;

    private final UUID uuid;
    private final String username;
    private final String password;
    private final String nome;
    private final boolean ativo;

    private Usuario(UUID uuid, String username, String password, String nome, boolean ativo) {
        this.uuid = Objects.requireNonNull(uuid, "uuid do usuario e obrigatorio");
        this.username = requireMaxLength(requireNotBlank(username, "username e obrigatorio"), MAX_USERNAME_LENGTH, "username deve ter no maximo 80 caracteres");
        this.password = requireMaxLength(requireNotBlank(password, "password e obrigatorio"), MAX_PASSWORD_LENGTH, "password deve ter no maximo 255 caracteres");
        this.nome = requireMaxLength(requireNotBlank(nome, "nome do usuario e obrigatorio"), MAX_NOME_LENGTH, "nome do usuario deve ter no maximo 120 caracteres");
        this.ativo = ativo;
    }

    public static Usuario reconstituir(UUID uuid, String username, String password, String nome, boolean ativo) {
        return new Usuario(uuid, username, password, nome, ativo);
    }

    private static String requireNotBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new DomainValidationException(message);
        }
        return value.trim();
    }

    private static String requireMaxLength(String value, int maxLength, String message) {
        if (value.length() > maxLength) {
            throw new DomainValidationException(message);
        }
        return value;
    }
}

