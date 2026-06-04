package br.com.catech.fire_shield_sms_ms.adapter.out.persistence.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sms_sms")
public class SmsJpaEntity {

    @Id
    private UUID uuid;

    @Column(name = "numero", nullable = false, length = 20)
    private String numeroDestino;

    @Column(nullable = false, length = 500)
    private String mensagem;

    @Column(name = "data_envio", nullable = false)
    private LocalDateTime dataEnvio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid_ocorrencia", nullable = false)
    private OcorrenciaJpaEntity ocorrencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uuid_contato", nullable = false)
    private ContatoJpaEntity contato;
}

