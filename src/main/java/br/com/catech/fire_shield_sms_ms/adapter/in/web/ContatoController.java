package br.com.catech.fire_shield_sms_ms.adapter.in.web;

import br.com.catech.fire_shield_sms_ms.adapter.in.web.dto.ContatoRequest;
import br.com.catech.fire_shield_sms_ms.adapter.in.web.dto.ContatoResponse;
import br.com.catech.fire_shield_sms_ms.adapter.in.web.mapper.ContatoWebMapper;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Contato;
import br.com.catech.fire_shield_sms_ms.application.ports.in.GerenciarContatoUseCase;

import java.util.List;
import java.util.UUID;

public class ContatoController {

    private final GerenciarContatoUseCase gerenciarContatoUseCase;

    public ContatoController(GerenciarContatoUseCase gerenciarContatoUseCase) {
        this.gerenciarContatoUseCase = gerenciarContatoUseCase;
    }

    public ContatoResponse criar(ContatoRequest request) {
        Contato contato = gerenciarContatoUseCase.criar(ContatoWebMapper.toDomain(request));
        return ContatoWebMapper.toResponse(contato);
    }

    public List<ContatoResponse> listar() {
        return gerenciarContatoUseCase.listar()
                .stream()
                .map(ContatoWebMapper::toResponse)
                .toList();
    }

    public ContatoResponse buscarPorUuid(UUID uuid) {
        Contato contato = gerenciarContatoUseCase.buscarPorUuid(uuid);
        return ContatoWebMapper.toResponse(contato);
    }

    public ContatoResponse atualizar(UUID uuid, ContatoRequest request) {
        Contato contato = gerenciarContatoUseCase.atualizar(uuid, ContatoWebMapper.toDomain(request));
        return ContatoWebMapper.toResponse(contato);
    }

    public void excluir(UUID uuid) {
        gerenciarContatoUseCase.excluir(uuid);
    }
}
