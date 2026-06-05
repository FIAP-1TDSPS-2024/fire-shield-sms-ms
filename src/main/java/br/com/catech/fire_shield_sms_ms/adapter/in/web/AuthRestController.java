package br.com.catech.fire_shield_sms_ms.adapter.in.web;

import br.com.catech.fire_shield_sms_ms.adapter.in.web.dto.LoginRequest;
import br.com.catech.fire_shield_sms_ms.adapter.in.web.dto.LoginResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthRestController {

    private final AuthController authController;

    public AuthRestController(AuthController authController) {
        this.authController = authController;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authController.login(request));
    }
}

