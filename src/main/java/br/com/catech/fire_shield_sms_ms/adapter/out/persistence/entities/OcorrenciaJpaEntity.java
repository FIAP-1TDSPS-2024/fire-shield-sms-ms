package br.com.catech.fire_shield_sms_ms.adapter.out.persistence.entities;

import br.com.catech.fire_shield_sms_ms.application.core.enums.SeveridadeOcorrenciaEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sms_ocorrencia")
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
    private LocalDateTime horarioDeteccao;

    @Column(length = 120)
    private String nome;

    @Column(length = 80)
    private String bairro;

    @Column(length = 80)
    private String cidade;

    @Column(name = "estado", nullable = false, length = 100)
    private String estado;

    @Column(length = 9)
    private String cep;

    @OneToMany(mappedBy = "ocorrencia")
    private List<SmsJpaEntity> sms;
}
