package br.com.catech.fire_shield_sms_ms.application.core.entities;

import br.com.catech.fire_shield_sms_ms.application.core.enums.SeveridadeOcorrenciaEnum;
import br.com.catech.fire_shield_sms_ms.application.core.exceptions.DomainValidationException;
import lombok.Getter;

import java.time.Instant;
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
    private final Instant horarioDeteccao;

    // Endereço – do estado para baixo
    private final String nome;
    private final String bairro;
    private final String cidade;
    private final String estadoSigla;
    private final String estadoNome;
    private final String cep;

    // -------------------------------------------------------------------------
    // Construtor privado – toda validação de invariantes fica aqui
    // -------------------------------------------------------------------------

    private Ocorrencia(UUID uuid,
                       double latitude,
                       double longitude,
                       SeveridadeOcorrenciaEnum severidade,
                       Instant horarioDeteccao,
                       String nome,
                       String bairro,
                       String cidade,
                       String estadoSigla,
                       String estadoNome,
                       String cep) {

        this.uuid            = Objects.requireNonNull(uuid, "uuid da ocorrência é obrigatório");
        this.latitude        = validarLatitude(latitude);
        this.longitude       = validarLongitude(longitude);
        this.severidade      = Objects.requireNonNull(severidade, "severidade é obrigatória");
        this.horarioDeteccao = validarHorario(horarioDeteccao);
        this.nome            = trimOrNull(nome);
        this.bairro          = trimOrNull(bairro);
        this.cidade          = trimOrNull(cidade);
        this.estadoSigla     = validarUf(estadoSigla);
        this.estadoNome      = requireNotBlank(estadoNome, "nome do estado é obrigatório");
        this.cep             = validarCep(cep);
    }

    // -------------------------------------------------------------------------
    // Factory methods
    // -------------------------------------------------------------------------

    public static Ocorrencia criarNova(double latitude,
                                       double longitude,
                                       SeveridadeOcorrenciaEnum severidade,
                                       Instant horarioDeteccao,
                                       String nome,
                                       String bairro,
                                       String cidade,
                                       String estadoSigla,
                                       String estadoNome,
                                       String cep) {
        return new Ocorrencia(UUID.randomUUID(), latitude, longitude, severidade,
                horarioDeteccao, nome, bairro, cidade, estadoSigla, estadoNome, cep);
    }

    public static Ocorrencia reconstituir(UUID uuid,
                                          double latitude,
                                          double longitude,
                                          SeveridadeOcorrenciaEnum severidade,
                                          Instant horarioDeteccao,
                                          String nome,
                                          String bairro,
                                          String cidade,
                                          String estadoSigla,
                                          String estadoNome,
                                          String cep) {
        return new Ocorrencia(uuid, latitude, longitude, severidade,
                horarioDeteccao, nome, bairro, cidade, estadoSigla, estadoNome, cep);
    }

    // -------------------------------------------------------------------------
    // Comportamentos de domínio
    // -------------------------------------------------------------------------

    /** Gera o corpo da mensagem SMS de alerta para esta ocorrência. */
    public String gerarMensagemAlerta() {
        StringBuilder sb = new StringBuilder();
        sb.append("🔥 ALERTA ").append(severidade)
          .append(" | ").append(estadoSigla);

        if (cidade != null)  sb.append("/").append(cidade);
        if (bairro != null)  sb.append(" | ").append(bairro);
        if (nome   != null)  sb.append(", ").append(nome);
        if (cep    != null)  sb.append(" | CEP ").append(cep);

        sb.append(" | Detectado: ").append(horarioDeteccao);
        return sb.toString();
    }

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

    private static Instant validarHorario(Instant horario) {
        Objects.requireNonNull(horario, "horário de detecção é obrigatório");
        if (horario.isAfter(Instant.now().plusSeconds(60))) {
            throw new DomainValidationException("horário de detecção não pode estar no futuro");
        }
        return horario;
    }

    private static String validarUf(String uf) {
        String sigla = requireNotBlank(uf, "sigla do estado é obrigatória").toUpperCase();
        if (sigla.length() != 2) {
            throw new DomainValidationException("sigla do estado deve conter exatamente 2 caracteres");
        }
        return sigla;
    }

    private static String validarCep(String cep) {
        if (cep == null || cep.isBlank()) {
            return null; // CEP é opcional
        }
        String soDigitos = cep.replaceAll("\\D", "");
        if (soDigitos.length() != 8) {
            throw new DomainValidationException("CEP deve conter 8 dígitos, recebido: " + cep);
        }
        return soDigitos;
    }
}
