package br.com.catech.fire_shield_sms_ms.adapter.out.persistence.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "sms_contato")
public class ContatoJpaEntity {

    @Id
    private UUID uuid;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(length = 255)
    private String descricao;

    @Column(nullable = false, length = 20)
    private String numero;

    @Column(name = "estado", nullable = false, length = 80)
    private String estado;

    @OneToMany(mappedBy = "contato")
    private List<SmsJpaEntity> mensagens;
}

