package br.com.catech.fire_shield_sms_ms.adapter.in.web;

import br.com.catech.fire_shield_sms_ms.adapter.in.web.dto.LoginRequest;
import br.com.catech.fire_shield_sms_ms.adapter.in.web.dto.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Autenticacao", description = "Endpoints publicos para autenticacao JWT.")
public class AuthRestController {

    private final AuthController authController;

    public AuthRestController(AuthController authController) {
        this.authController = authController;
    }

    @PostMapping("/login")
    @Operation(
            summary = "Realiza login",
            description = "Autentica o usuario base da aplicacao e retorna um token JWT para acesso aos endpoints protegidos."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Login realizado com sucesso",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Credenciais invalidas",
                    content = @Content(
                            examples = @ExampleObject(value = """
                                    {
                                      "timestamp": "2026-06-05T11:53:43.8812128",
                                      "status": 401,
                                      "error": "Unauthorized",
                                      "messages": ["credenciais invalidas"]
                                    }
                                    """)
                    )
            )
    })
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authController.login(request));
    }
}
