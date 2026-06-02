package br.com.catech.fire_shield_sms_ms.adapter.out.persistence.entities;

import br.com.catech.fire_shield_sms_ms.application.core.enums.SeveridadeOcorrenciaEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ocorrencia")
public class OcorrenciaJpaEntity {

    @Id
    private UUID uuid;

    @Column(nullable = false)
    private double latitude;

    @Column(nullable = false)
    private double longitude;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SeveridadeOcorrenciaEnum severidade;

    @Column(name = "horario_deteccao", nullable = false)
    private Instant horarioDeteccao;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, length = 80)
    private String bairro;

    @Column(nullable = false, length = 80)
    private String cidade;

    @Column(name = "estado_sigla", nullable = false, length = 2)
    private String estadoSigla;

    @Column(name = "estado_nome", nullable = false, length = 80)
    private String estadoNome;

    @Column(nullable = false, length = 8)
    private String cep;
}

