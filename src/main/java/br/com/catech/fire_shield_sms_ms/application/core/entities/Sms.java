package br.com.catech.fire_shield_sms_ms.application.core.entities;

import br.com.catech.fire_shield_sms_ms.application.core.exceptions.DomainValidationException;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

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
    private final String numeroDestino;
    private final String mensagem;
    private final LocalDateTime dataEnvio;
    private final Ocorrencia ocorrencia;
    private final Contato contato;

    // -------------------------------------------------------------------------
    // Construtor privado – toda validação de invariantes fica aqui
    // -------------------------------------------------------------------------

    private Sms(UUID uuid,
                String numeroDestino,
                String mensagem,
                LocalDateTime dataEnvio, Ocorrencia ocorrencia, Contato contato) {

        this.uuid           = Objects.requireNonNull(uuid, "uuid do SMS é obrigatório");
        this.numeroDestino  = validarTelefone(numeroDestino, "número de destino inválido");
        this.mensagem       = validarMensagem(mensagem);
        this.dataEnvio      = Objects.requireNonNull(dataEnvio, "data de envio é obrigatória");
        this.ocorrencia = ocorrencia;
        this.contato = contato;
    }

    public Sms(UUID uuid, String numeroDestino, String mensagem, Ocorrencia ocorrencia, Contato contato) {

        this.uuid           = Objects.requireNonNull(uuid, "uuid do SMS é obrigatório");
        this.numeroDestino  = validarTelefone(numeroDestino, "número de destino inválido");
        this.mensagem       = validarMensagem(mensagem);
        this.dataEnvio      = LocalDateTime.now();
        this.ocorrencia = ocorrencia;
        this.contato = contato;
    }

    // -------------------------------------------------------------------------
    // Factory methods
    // -------------------------------------------------------------------------

    public static Sms criarNovo(String numeroDestino,
                                String mensagem,
                                Ocorrencia ocorrencia,
                                Contato contato) {
        return new Sms(UUID.randomUUID(), numeroDestino, mensagem, ocorrencia, contato);
    }

    public static Sms reconstituir(UUID uuid,
                                   String numeroDestino,
                                   String mensagem,
                                   LocalDateTime dataEnvio,
                                   Ocorrencia ocorrencia,
                                   Contato contato) {
        return new Sms(uuid, numeroDestino, mensagem, dataEnvio, ocorrencia, contato);
    }

    public static String criarMensagem(
            Ocorrencia ocorrencia,
            Contato contato) {

        StringBuilder mensagem = new StringBuilder();

        mensagem.append("ALERTA DE INCÊNDIO\n\n");

        mensagem.append("Órgão responsável: ")
                .append(contato.getNome())
                .append("\n");

        mensagem.append("Severidade: ")
                .append(ocorrencia.getSeveridade())
                .append("\n");

        mensagem.append("Data/Hora da detecção: ")
                .append(ocorrencia.getHorarioDeteccao())
                .append("\n\n");

        mensagem.append("Localização:\n");

        if (possuiTexto(ocorrencia.getNome())) {
            mensagem.append("- Referência: ")
                    .append(ocorrencia.getNome())
                    .append("\n");
        }

        if (possuiTexto(ocorrencia.getBairro())) {
            mensagem.append("- Bairro: ")
                    .append(ocorrencia.getBairro())
                    .append("\n");
        }

        if (possuiTexto(ocorrencia.getCidade())) {
            mensagem.append("- Cidade: ")
                    .append(ocorrencia.getCidade())
                    .append("\n");
        }

        mensagem.append("- Estado: ")
                .append(ocorrencia.getEstado())
                .append("\n");

        if (possuiTexto(ocorrencia.getCep())) {
            mensagem.append("- CEP: ")
                    .append(ocorrencia.getCep())
                    .append("\n");
        }

        mensagem.append("- Latitude: ")
                .append(ocorrencia.getLatitude())
                .append("\n");

        mensagem.append("- Longitude: ")
                .append(ocorrencia.getLongitude())
                .append("\n");

        mensagem.append("\nFavor verificar a ocorrência.");

        return mensagem.toString();
    }

    private static boolean possuiTexto(String valor) {
        return valor != null && !valor.isBlank();
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
