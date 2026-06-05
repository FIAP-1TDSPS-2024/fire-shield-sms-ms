package br.com.catech.fire_shield_sms_ms.application.core.entities;

import br.com.catech.fire_shield_sms_ms.application.core.exceptions.DomainValidationException;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

@Getter
public class Contato {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?\\d{10,15}$");
    private static final int MAX_NOME_LENGTH = 120;
    private static final int MAX_DESCRICAO_LENGTH = 255;
    private static final int MAX_NUMERO_LENGTH = 20;
    private static final int MAX_ESTADO_LENGTH = 80;

    private final UUID uuid;
    private final String nome;
    private final String descricao;
    private final String numero;
    private final String estado;

    private Contato(UUID uuid,
                    String nome,
                    String descricao,
                    String numero,
                    String estado) {

        this.uuid = Objects.requireNonNull(uuid, "uuid do contato e obrigatorio");
        this.nome = requireMaxLength(
                requireNotBlank(nome, "nome do contato e obrigatorio"),
                MAX_NOME_LENGTH,
                "nome do contato deve ter no maximo 120 caracteres"
        );
        this.descricao = descricao != null
                ? requireMaxLength(descricao.trim(), MAX_DESCRICAO_LENGTH, "descricao deve ter no maximo 255 caracteres")
                : null;
        this.numero = validarTelefone(numero);
        this.estado = requireMaxLength(
                requireNotBlank(estado, "estado e obrigatorio"),
                MAX_ESTADO_LENGTH,
                "estado deve ter no maximo 80 caracteres"
        );
    }

    public static Contato criarNovo(String nome,
                                    String descricao,
                                    String numero,
                                    String estado) {
        return new Contato(UUID.randomUUID(), nome, descricao, numero, estado);
    }

    public static Contato reconstituir(UUID uuid,
                                       String nome,
                                       String descricao,
                                       String numero,
                                       String estado) {
        return new Contato(uuid, nome, descricao, numero, estado);
    }

    private static String requireNotBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new DomainValidationException(message);
        }
        return value.trim();
    }

    private static String validarTelefone(String numero) {
        String sanitizado = requireNotBlank(numero, "numero do contato e obrigatorio")
                .replaceAll("[\\s()\\-]", "");
        if (sanitizado.length() > MAX_NUMERO_LENGTH) {
            throw new DomainValidationException("numero do contato deve ter no maximo 20 caracteres");
        }
        if (!PHONE_PATTERN.matcher(sanitizado).matches()) {
            throw new DomainValidationException(
                    "numero de telefone invalido: deve conter entre 10 e 15 digitos");
        }
        return sanitizado;
    }

    private static String requireMaxLength(String value, int maxLength, String message) {
        if (value.length() > maxLength) {
            throw new DomainValidationException(message);
        }
        return value;
    }
}
