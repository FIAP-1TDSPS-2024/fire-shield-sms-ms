package br.com.catech.fire_shield_sms_ms.application.core.entities;

import br.com.catech.fire_shield_sms_ms.application.core.exceptions.DomainValidationException;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Entidade de domínio Sms.
 *
 * <p>Representa uma mensagem de texto enviada como alerta de incêndio.
 * Imutável após a construção.
 *
 * <p>Factory methods:
 * <ul>
 *   <li>{@link #criarNovo} – novo SMS (gera UUID automaticamente)</li>
 *   <li>{@link #reconstituir} – reconstitui a partir da persistência</li>
 * </ul>
 */
@Getter
public class Sms {

    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\+?\\d{10,15}$");
    private static final int MENSAGEM_MAX_CHARS = 500;

    private final UUID uuid;
    private final String numeroOrigem;
    private final String numeroDestino;
    private final String mensagem;
    private final LocalDateTime dataEnvio;

    // -------------------------------------------------------------------------
    // Construtor privado – toda validação de invariantes fica aqui
    // -------------------------------------------------------------------------

    private Sms(UUID uuid,
                String numeroOrigem,
                String numeroDestino,
                String mensagem,
                LocalDateTime dataEnvio) {

        this.uuid           = Objects.requireNonNull(uuid, "uuid do SMS é obrigatório");
        this.numeroOrigem   = validarTelefone(numeroOrigem, "número de origem inválido");
        this.numeroDestino  = validarTelefone(numeroDestino, "número de destino inválido");
        this.mensagem       = validarMensagem(mensagem);
        this.dataEnvio      = Objects.requireNonNull(dataEnvio, "data de envio é obrigatória");
    }

    // -------------------------------------------------------------------------
    // Factory methods
    // -------------------------------------------------------------------------

    public static Sms criarNovo(String numeroOrigem,
                                String numeroDestino,
                                String mensagem,
                                LocalDateTime dataEnvio) {
        return new Sms(UUID.randomUUID(), numeroOrigem, numeroDestino, mensagem, dataEnvio);
    }

    public static Sms reconstituir(UUID uuid,
                                   String numeroOrigem,
                                   String numeroDestino,
                                   String mensagem,
                                   LocalDateTime dataEnvio) {
        return new Sms(uuid, numeroOrigem, numeroDestino, mensagem, dataEnvio);
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

    private static String validarTelefone(String numero, String message) {
        String sanitizado = requireNotBlank(numero, message)
                .replaceAll("[\\s()\\-]", "");
        if (!PHONE_PATTERN.matcher(sanitizado).matches()) {
            throw new DomainValidationException(message + ": deve conter entre 10 e 15 dígitos");
        }
        return sanitizado;
    }

    private static String validarMensagem(String mensagem) {
        String value = requireNotBlank(mensagem, "mensagem do SMS é obrigatória");
        if (value.length() > MENSAGEM_MAX_CHARS) {
            throw new DomainValidationException(
                    "mensagem do SMS deve ter no máximo " + MENSAGEM_MAX_CHARS + " caracteres");
        }
        return value;
    }
}
