package br.com.catech.fire_shield_sms_ms.adapter.out.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Column(name = "estado_sigla", nullable = false, length = 2)
    private String estadoSigla;

    @Column(name = "estado_nome", nullable = false, length = 80)
    private String estadoNome;
}

