package br.com.catech.fire_shield_sms_ms.application.core.entities;

import br.com.catech.fire_shield_sms_ms.application.core.enums.SeveridadeOcorrenciaEnum;
import br.com.catech.fire_shield_sms_ms.application.core.exceptions.DomainValidationException;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Entidade de domínio Ocorrencia.
 *
 * <p>Representa um evento de incêndio detectado com sua localização geográfica,
 * endereço e nível de severidade. Imutável após a construção.
 *
 * <p>Factory methods:
 * <ul>
 *   <li>{@link #criarNova} – nova ocorrência (gera UUID automaticamente)</li>
 *   <li>{@link #reconstituir} – reconstitui a partir da persistência</li>
 * </ul>
 */
@Getter
public class Ocorrencia {

    private final UUID uuid;
    private final double latitude;
    private final double longitude;
    private final SeveridadeOcorrenciaEnum severidade;
    private final LocalDateTime horarioDeteccao;

    // Endereço – campos opcionais (incêndios em área rural podem não tê-los)
    private final String nome;
    private final String bairro;
    private final String cidade;
    private final String estado;
    private final String cep;

    // -------------------------------------------------------------------------
    // Construtor privado – toda validação de invariantes fica aqui
    // -------------------------------------------------------------------------

    private Ocorrencia(UUID uuid,
                       double latitude,
                       double longitude,
                       SeveridadeOcorrenciaEnum severidade,
                       LocalDateTime horarioDeteccao,
                       String nome,
                       String bairro,
                       String cidade,
                       String estado,
                       String cep) {

        this.uuid            = Objects.requireNonNull(uuid, "uuid da ocorrência é obrigatório");
        this.latitude        = validarLatitude(latitude);
        this.longitude       = validarLongitude(longitude);
        this.severidade      = Objects.requireNonNull(severidade, "severidade é obrigatória");
        this.horarioDeteccao = validarHorario(horarioDeteccao);
        this.nome            = trimOrNull(nome);
        this.bairro          = trimOrNull(bairro);
        this.cidade          = trimOrNull(cidade);
        this.estado          = requireNotBlank(estado, "estado é obrigatório");
        this.cep             = validarCep(cep);
    }

    // -------------------------------------------------------------------------
    // Factory methods
    // -------------------------------------------------------------------------

    public static Ocorrencia criarNova(double latitude,
                                       double longitude,
                                       SeveridadeOcorrenciaEnum severidade,
                                       LocalDateTime horarioDeteccao,
                                       String nome,
                                       String bairro,
                                       String cidade,
                                       String estado,
                                       String cep) {
        return new Ocorrencia(UUID.randomUUID(), latitude, longitude, severidade,
                horarioDeteccao, nome, bairro, cidade, estado, cep);
    }

    public static Ocorrencia reconstituir(UUID uuid,
                                          double latitude,
                                          double longitude,
                                          SeveridadeOcorrenciaEnum severidade,
                                          LocalDateTime horarioDeteccao,
                                          String nome,
                                          String bairro,
                                          String cidade,
                                          String estado,
                                          String cep) {
        return new Ocorrencia(uuid, latitude, longitude, severidade,
                horarioDeteccao, nome, bairro, cidade, estado, cep);
    }

    // -------------------------------------------------------------------------
    // Comportamentos de domínio
    // -------------------------------------------------------------------------

    public boolean isCritica() {
        return SeveridadeOcorrenciaEnum.CRITICO == severidade;
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

    /** Retorna o valor aparado ou {@code null} se vazio/nulo — para campos opcionais. */
    private static String trimOrNull(String value) {
        return (value == null || value.isBlank()) ? null : value.trim();
    }

    private static double validarLatitude(double latitude) {
        if (latitude < -90 || latitude > 90) {
            throw new DomainValidationException("latitude deve estar entre -90 e 90, recebido: " + latitude);
        }
        return latitude;
    }

    private static double validarLongitude(double longitude) {
        if (longitude < -180 || longitude > 180) {
            throw new DomainValidationException("longitude deve estar entre -180 e 180, recebido: " + longitude);
        }
        return longitude;
    }

    private static LocalDateTime validarHorario(LocalDateTime horario) {
        Objects.requireNonNull(horario, "horário de detecção é obrigatório");
        if (horario.isAfter(LocalDateTime.now().plusSeconds(60))) {
            throw new DomainValidationException("horário de detecção não pode estar no futuro");
        }
        return horario;
    }

    private static String validarCep(String cep) {
        if (cep == null || cep.isBlank()) {
            return null; // CEP é opcional
        }
        String trimmed = cep.trim();
        // Aceita tanto "20020100" quanto "20020-100"
        if (!trimmed.matches("\\d{5}-\\d{3}") && !trimmed.matches("\\d{8}")) {
            throw new DomainValidationException(
                    "CEP deve estar no formato XXXXX-XXX ou XXXXXXXX, recebido: " + cep);
        }
        // Normaliza sempre para o formato com hífen: XXXXX-XXX
        if (trimmed.length() == 8) {
            trimmed = trimmed.substring(0, 5) + "-" + trimmed.substring(5);
        }
        return trimmed;
    }
}
