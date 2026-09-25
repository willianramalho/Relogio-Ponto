package com.pontoflex.web.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.pontoflex.application.dto.AutenticacaoResponse;
import com.pontoflex.application.dto.LoginRequest;
import com.pontoflex.application.dto.RegistroRequest;
import com.pontoflex.application.dto.RegistroResponse;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class AuthControllerIT {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

    @Autowired
    private TestRestTemplate restTemplate;

    private RegistroRequest novoRegistro(String email, String cpf, String matricula) {
        return new RegistroRequest(
                "Ana Souza", cpf, matricula, LocalDate.of(2024, 1, 15), email, "SenhaForte123");
    }

    @Test
    @DisplayName("Deve registrar um novo colaborador e em seguida autenticar e receber um JWT valido")
    void registrarELogarDeveRetornarTokenValido() {
        RegistroRequest registro = novoRegistro("ana.integracao@example.com", "11111111111", "MAT-IT-0001");

        ResponseEntity<RegistroResponse> respostaRegistro =
                restTemplate.postForEntity("/api/auth/registrar", registro, RegistroResponse.class);

        assertThat(respostaRegistro.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(respostaRegistro.getBody()).isNotNull();
        assertThat(respostaRegistro.getBody().email()).isEqualTo("ana.integracao@example.com");

        LoginRequest login = new LoginRequest("ana.integracao@example.com", "SenhaForte123");
        ResponseEntity<AutenticacaoResponse> respostaLogin =
                restTemplate.postForEntity("/api/auth/login", login, AutenticacaoResponse.class);

        assertThat(respostaLogin.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(respostaLogin.getBody()).isNotNull();
        assertThat(respostaLogin.getBody().token()).isNotBlank();
        assertThat(respostaLogin.getBody().roles()).containsExactly("COLABORADOR");
    }

    @Test
    @DisplayName("Nao deve permitir registrar dois usuarios com o mesmo email")
    void registrarComEmailDuplicadoDeveRetornar409() {
        RegistroRequest primeiro = novoRegistro("duplicado@example.com", "22222222222", "MAT-IT-0002");
        restTemplate.postForEntity("/api/auth/registrar", primeiro, RegistroResponse.class);

        RegistroRequest segundo = novoRegistro("duplicado@example.com", "33333333333", "MAT-IT-0003");
        ResponseEntity<ErroResponse> resposta =
                restTemplate.postForEntity("/api/auth/registrar", segundo, ErroResponse.class);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().codigo()).isEqualTo("EMAIL_JA_CADASTRADO");
    }

    @Test
    @DisplayName("Login com senha incorreta deve retornar 401")
    void loginComSenhaErradaDeveRetornar401() {
        RegistroRequest registro = novoRegistro("senha.errada@example.com", "44444444444", "MAT-IT-0004");
        restTemplate.postForEntity("/api/auth/registrar", registro, RegistroResponse.class);

        LoginRequest login = new LoginRequest("senha.errada@example.com", "senhaTotalmenteErrada");
        ResponseEntity<ErroResponse> resposta = restTemplate.postForEntity("/api/auth/login", login, ErroResponse.class);

        assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        assertThat(resposta.getBody()).isNotNull();
        assertThat(resposta.getBody().codigo()).isEqualTo("CREDENCIAIS_INVALIDAS");
    }
}
