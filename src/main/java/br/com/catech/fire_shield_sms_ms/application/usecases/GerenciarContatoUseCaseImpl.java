package br.com.catech.fire_shield_sms_ms.application.usecases;

import br.com.catech.fire_shield_sms_ms.application.core.entities.Contato;
import br.com.catech.fire_shield_sms_ms.application.core.exceptions.NotFoundException;
import br.com.catech.fire_shield_sms_ms.application.ports.in.GerenciarContatoUseCase;
import br.com.catech.fire_shield_sms_ms.application.ports.out.ContatoRepository;

import java.util.List;
import java.util.UUID;

public class GerenciarContatoUseCaseImpl implements GerenciarContatoUseCase {

    private final ContatoRepository contatoRepository;

    public GerenciarContatoUseCaseImpl(ContatoRepository contatoRepository) {
        this.contatoRepository = contatoRepository;
    }

    @Override
    public Contato criar(Contato contato) {
        return contatoRepository.save(contato);
    }

    @Override
    public List<Contato> listar() {
        return contatoRepository.findAll();
    }

    @Override
    public Contato buscarPorUuid(UUID uuid) {
        return contatoRepository.findByUuid(uuid)
                .orElseThrow(() -> contatoNaoEncontrado(uuid));
    }

    @Override
    public Contato atualizar(UUID uuid, Contato contato) {
        if (!contatoRepository.existsByUuid(uuid)) {
            throw contatoNaoEncontrado(uuid);
        }

        Contato contatoAtualizado = Contato.reconstituir(
                uuid,
                contato.getNome(),
                contato.getDescricao(),
                contato.getNumero(),
                contato.getEstado()
        );

        return contatoRepository.save(contatoAtualizado);
    }

    @Override
    public void excluir(UUID uuid) {
        if (!contatoRepository.existsByUuid(uuid)) {
            throw contatoNaoEncontrado(uuid);
        }

        contatoRepository.deleteByUuid(uuid);
    }

    private NotFoundException contatoNaoEncontrado(UUID uuid) {
        return new NotFoundException("Contato nao encontrado: " + uuid);
    }
}
