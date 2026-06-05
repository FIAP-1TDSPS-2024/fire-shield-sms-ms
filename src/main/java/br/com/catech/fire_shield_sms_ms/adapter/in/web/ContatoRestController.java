package br.com.catech.fire_shield_sms_ms.adapter.in.web;

import br.com.catech.fire_shield_sms_ms.adapter.in.web.dto.ContatoRequest;
import br.com.catech.fire_shield_sms_ms.adapter.in.web.dto.ContatoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/contatos")
public class ContatoRestController {

    private final ContatoController contatoController;

    public ContatoRestController(ContatoController contatoController) {
        this.contatoController = contatoController;
    }

    @PostMapping
    public ResponseEntity<ContatoResponse> criar(@RequestBody ContatoRequest request) {
        ContatoResponse response = contatoController.criar(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(response.uuid())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ContatoResponse>> listar() {
        return ResponseEntity.ok(contatoController.listar());
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<ContatoResponse> buscarPorUuid(@PathVariable UUID uuid) {
        return ResponseEntity.ok(contatoController.buscarPorUuid(uuid));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<ContatoResponse> atualizar(
            @PathVariable UUID uuid,
            @RequestBody ContatoRequest request
    ) {
        return ResponseEntity.ok(contatoController.atualizar(uuid, request));
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> excluir(@PathVariable UUID uuid) {
        contatoController.excluir(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

