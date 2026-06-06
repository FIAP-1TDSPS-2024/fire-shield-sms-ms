package br.com.catech.fire_shield_sms_ms.adapter.in.web;

import br.com.catech.fire_shield_sms_ms.adapter.in.web.dto.ContatoRequest;
import br.com.catech.fire_shield_sms_ms.adapter.in.web.dto.ContatoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Contatos", description = "CRUD de contatos dos orgaos responsaveis por estado.")
@SecurityRequirement(name = "bearerAuth")
public class ContatoRestController {

    private final ContatoController contatoController;

    public ContatoRestController(ContatoController contatoController) {
        this.contatoController = contatoController;
    }

    @PostMapping
    @Operation(summary = "Cria um contato", description = "Cadastra um contato responsavel por receber notificacoes de ocorrencias.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Contato criado",
                    content = @Content(schema = @Schema(implementation = ContatoResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Dados invalidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "JWT ausente ou invalido", content = @Content)
    })
    public ResponseEntity<ContatoResponse> criar(@RequestBody ContatoRequest request) {
        ContatoResponse response = contatoController.criar(request);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{uuid}")
                .buildAndExpand(response.uuid())
                .toUri();

        return ResponseEntity.created(location).body(response);
    }

    @GetMapping
    @Operation(summary = "Lista contatos", description = "Retorna todos os contatos cadastrados.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contatos encontrados",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ContatoResponse.class)))
            ),
            @ApiResponse(responseCode = "401", description = "JWT ausente ou invalido", content = @Content)
    })
    public ResponseEntity<List<ContatoResponse>> listar() {
        return ResponseEntity.ok(contatoController.listar());
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Busca contato por UUID", description = "Retorna um contato especifico pelo identificador.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contato encontrado",
                    content = @Content(schema = @Schema(implementation = ContatoResponse.class))
            ),
            @ApiResponse(responseCode = "401", description = "JWT ausente ou invalido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Contato nao encontrado", content = @Content)
    })
    public ResponseEntity<ContatoResponse> buscarPorUuid(
            @Parameter(description = "UUID do contato", example = "0190b1f5-8d5c-7d75-9f46-1e9e5f0a0001")
            @PathVariable UUID uuid
    ) {
        return ResponseEntity.ok(contatoController.buscarPorUuid(uuid));
    }

    @PutMapping("/{uuid}")
    @Operation(summary = "Atualiza contato", description = "Atualiza os dados cadastrais de um contato existente.")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Contato atualizado",
                    content = @Content(schema = @Schema(implementation = ContatoResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Dados invalidos", content = @Content),
            @ApiResponse(responseCode = "401", description = "JWT ausente ou invalido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Contato nao encontrado", content = @Content)
    })
    public ResponseEntity<ContatoResponse> atualizar(
            @Parameter(description = "UUID do contato", example = "0190b1f5-8d5c-7d75-9f46-1e9e5f0a0001")
            @PathVariable UUID uuid,
            @RequestBody ContatoRequest request
    ) {
        return ResponseEntity.ok(contatoController.atualizar(uuid, request));
    }

    @DeleteMapping("/{uuid}")
    @Operation(summary = "Exclui contato", description = "Remove um contato cadastrado.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Contato excluido", content = @Content),
            @ApiResponse(responseCode = "401", description = "JWT ausente ou invalido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Contato nao encontrado", content = @Content)
    })
    public ResponseEntity<Void> excluir(
            @Parameter(description = "UUID do contato", example = "0190b1f5-8d5c-7d75-9f46-1e9e5f0a0001")
            @PathVariable UUID uuid
    ) {
        contatoController.excluir(uuid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
