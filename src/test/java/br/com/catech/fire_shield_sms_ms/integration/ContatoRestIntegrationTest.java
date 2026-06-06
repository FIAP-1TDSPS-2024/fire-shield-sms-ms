package br.com.catech.fire_shield_sms_ms.integration;

import br.com.catech.fire_shield_sms_ms.adapter.out.persistence.repository.ContatoJpaRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ContatoRestIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ContatoJpaRepository contatoJpaRepository;

    @Test
    void deveExecutarFluxoCompletoDeLoginECrudDeContatos() throws Exception {
        String token = login();

        mockMvc.perform(get("/api/v1/contatos"))
                .andExpect(status().isUnauthorized());

        String createResponse = mockMvc.perform(post("/api/v1/contatos")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Defesa Civil Integracao",
                                  "descricao": "Contato criado por teste de integracao",
                                  "numero": "+5511988887777",
                                  "estado": "Estado Integracao"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.nome").value("Defesa Civil Integracao"))
                .andExpect(jsonPath("$.numero").value("+5511988887777"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        UUID uuid = UUID.fromString(objectMapper.readTree(createResponse).get("uuid").asText());
        assertThat(contatoJpaRepository.existsById(uuid)).isTrue();

        mockMvc.perform(get("/api/v1/contatos/" + uuid)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("Estado Integracao"));

        mockMvc.perform(put("/api/v1/contatos/" + uuid)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "nome": "Defesa Civil Atualizada",
                                  "descricao": "Contato atualizado por teste",
                                  "numero": "+5511977776666",
                                  "estado": "Estado Atualizado"
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(uuid.toString()))
                .andExpect(jsonPath("$.nome").value("Defesa Civil Atualizada"))
                .andExpect(jsonPath("$.estado").value("Estado Atualizado"));

        mockMvc.perform(get("/api/v1/contatos")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[?(@.uuid == '" + uuid + "')]").exists());

        mockMvc.perform(delete("/api/v1/contatos/" + uuid)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());

        assertThat(contatoJpaRepository.existsById(uuid)).isFalse();
    }

    @Test
    void deveRetornarUnauthorizedParaCredenciaisInvalidas() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "admin",
                                  "password": "senha-errada"
                                }
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.messages[0]").value("credenciais invalidas"));
    }

    private String login() throws Exception {
        String response = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "admin",
                                  "password": "admin123"
                                }
                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tokenType").value("Bearer"))
                .andExpect(jsonPath("$.accessToken").isNotEmpty())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode json = objectMapper.readTree(response);
        return json.get("accessToken").asText();
    }
}
