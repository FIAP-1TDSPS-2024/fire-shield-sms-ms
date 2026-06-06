package br.com.catech.fire_shield_sms_ms.framework.config.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Fire Shield SMS MS API",
                version = "0.0.1",
                description = "API REST do microsservico responsavel por contatos, autenticacao JWT e notificacoes SMS do projeto Fire Shield.",
                contact = @Contact(name = "Catech"),
                license = @License(name = "Academic Project")
        )
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

    @Bean
    OpenAPI fireShieldOpenApi() {
        return new OpenAPI()
                .servers(List.of(new Server()
                        .url("http://localhost:8080")
                        .description("Local development")))
                .externalDocs(new ExternalDocumentation()
                        .description("MCP endpoint")
                        .url("http://localhost:8080/mcp"));
    }
}

