package com.pontoflex.infrastructure.security;

import static org.assertj.core.api.Assertions.assertThat;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

class JwtServiceTest {

    private static final String SEGREDO_TESTE = "segredo-de-teste-com-pelo-menos-32-bytes-de-tamanho";

    private final JwtService jwtService = new JwtService(SEGREDO_TESTE, 480);

    @Test
    @DisplayName("Deve gerar um token cujo email extraido bate com o email usado na geracao")
    void gerarTokenERecuperarEmailDeveFazerRoundTrip() {
        String token = jwtService.gerarToken("ana@example.com", List.of("COLABORADOR"));

        assertThat(jwtService.extrairEmail(token)).isEqualTo("ana@example.com");
        assertThat(jwtService.extrairRoles(token)).containsExactly("COLABORADOR");
    }

    @Test
    @DisplayName("Token gerado deve ser considerado valido para o usuario correspondente")
    void tokenGeradoDeveSerValidoParaOUsuarioCorrespondente() {
        String token = jwtService.gerarToken("ana@example.com", List.of("COLABORADOR"));
        UserDetails userDetails = new User("ana@example.com", "hash-qualquer", List.of());

        assertThat(jwtService.tokenValido(token, userDetails)).isTrue();
    }

    @Test
    @DisplayName("Token nao deve ser valido para um usuario diferente do subject")
    void tokenNaoDeveSerValidoParaUsuarioDiferente() {
        String token = jwtService.gerarToken("ana@example.com", List.of("COLABORADOR"));
        UserDetails outroUsuario = new User("outro@example.com", "hash-qualquer", List.of());

        assertThat(jwtService.tokenValido(token, outroUsuario)).isFalse();
    }

    @Test
    @DisplayName("Token expirado nao deve ser considerado valido")
    void tokenExpiradoNaoDeveSerValido() {
        SecretKey chave = Keys.hmacShaKeyFor(SEGREDO_TESTE.getBytes(StandardCharsets.UTF_8));
        Date expiradoHaDezSegundos = new Date(System.currentTimeMillis() - 10_000);

        String tokenExpirado = Jwts.builder()
                .subject("ana@example.com")
                .issuedAt(new Date(System.currentTimeMillis() - 20_000))
                .expiration(expiradoHaDezSegundos)
                .signWith(chave)
                .compact();

        UserDetails userDetails = new User("ana@example.com", "hash-qualquer", List.of());

        assertThat(jwtService.tokenValido(tokenExpirado, userDetails)).isFalse();
    }

    @Test
    @DisplayName("Token adulterado nao deve ser considerado valido")
    void tokenAdulteradoNaoDeveSerValido() {
        String token = jwtService.gerarToken("ana@example.com", List.of("COLABORADOR"));
        String tokenAdulterado = token.substring(0, token.length() - 2) + "xx";
        UserDetails userDetails = new User("ana@example.com", "hash-qualquer", List.of());

        assertThat(jwtService.tokenValido(tokenAdulterado, userDetails)).isFalse();
    }
}
