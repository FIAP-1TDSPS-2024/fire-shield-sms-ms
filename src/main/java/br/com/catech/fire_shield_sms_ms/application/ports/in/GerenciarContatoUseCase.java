package br.com.catech.fire_shield_sms_ms.application.ports.in;

import br.com.catech.fire_shield_sms_ms.application.core.entities.Contato;

import java.util.List;
import java.util.UUID;

public interface GerenciarContatoUseCase {
    Contato criar(Contato contato);
    List<Contato> listar();
    Contato buscarPorUuid(UUID uuid);
    Contato atualizar(UUID uuid, Contato contato);
    void excluir(UUID uuid);
}
