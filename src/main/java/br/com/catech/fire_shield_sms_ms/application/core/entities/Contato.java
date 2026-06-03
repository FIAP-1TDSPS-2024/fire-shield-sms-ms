package br.com.catech.fire_shield_sms_ms.application.core.entities;

import br.com.catech.fire_shield_sms_ms.application.core.exceptions.DomainValidationException;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Entidade de domínio Contato.
 *
 * <p>Instâncias são criadas exclusivamente via factory methods:
 * <ul>
 *   <li>{@link #criarNovo} – para novos contatos (gera UUID automaticamente)</li>
 *   <li>{@link #reconstituir} – para reconstituir do banco sem gerar novo UUID</li>
 * </ul>
 * Todos os campos são imutáveis após a construção.
 */
@Getter
public class Contato {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?\\d{10,15}$");

    private final UUID uuid;
    private final String nome;
    private final String descricao;
    private final String numero;
    private final String estado;

    // -------------------------------------------------------------------------
    // Construtor privado – toda validação de invariantes fica aqui
    // -------------------------------------------------------------------------

    private Contato(UUID uuid,
                    String nome,
                    String descricao,
                    String numero,
                    String estado) {

        this.uuid       = Objects.requireNonNull(uuid, "uuid do contato é obrigatório");
        this.nome       = requireNotBlank(nome, "nome do contato é obrigatório");
        this.descricao  = descricao != null ? descricao.trim() : null;
        this.numero     = validarTelefone(numero);
        this.estado     = requireNotBlank(estado, "O estado é obrigatório");
    }

    // -------------------------------------------------------------------------
    // Factory methods
    // -------------------------------------------------------------------------

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

    // -------------------------------------------------------------------------
    // Helpers de validação
    // -------------------------------------------------------------------------

    private static String requireNotBlank(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new DomainValidationException(message);
        }
        return value.trim();
    }

    private static String validarTelefone(String numero) {
        String sanitizado = requireNotBlank(numero, "número do contato é obrigatório")
                .replaceAll("[\\s()\\-]", "");
        if (!PHONE_PATTERN.matcher(sanitizado).matches()) {
            throw new DomainValidationException(
                    "número de telefone inválido: deve conter entre 10 e 15 dígitos");
        }
        return sanitizado;
    }
}
