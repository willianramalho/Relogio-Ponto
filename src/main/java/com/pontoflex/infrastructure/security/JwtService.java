package com.pontoflex.infrastructure.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

    private static final String CLAIM_ROLES = "roles";

    private final SecretKey chaveAssinatura;
    private final long expiracaoMinutos;

    public JwtService(
            @Value("${pontoflex.jwt.secret}") String secret,
            @Value("${pontoflex.jwt.expiracao-minutos}") long expiracaoMinutos) {
        this.chaveAssinatura = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expiracaoMinutos = expiracaoMinutos;
    }

    public long getExpiracaoMinutos() {
        return expiracaoMinutos;
    }

    public String gerarToken(String email, List<String> roles) {
        Date agora = new Date();
        Date expiracao = new Date(agora.getTime() + expiracaoMinutos * 60_000);

        return Jwts.builder()
                .subject(email)
                .claim(CLAIM_ROLES, roles)
                .issuedAt(agora)
                .expiration(expiracao)
                .signWith(chaveAssinatura)
                .compact();
    }

    public String extrairEmail(String token) {
        return extrairTodasClaims(token).getSubject();
    }

    public List<String> extrairRoles(String token) {
        List<?> roles = extrairTodasClaims(token).get(CLAIM_ROLES, List.class);
        return roles == null
                ? List.of()
                : roles.stream().map(Object::toString).collect(Collectors.toList());
    }

    public boolean tokenValido(String token, UserDetails userDetails) {
        try {
            String email = extrairEmail(token);
            return email.equals(userDetails.getUsername()) && !tokenExpirado(token);
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    private boolean tokenExpirado(String token) {
        return extrairTodasClaims(token).getExpiration().before(new Date());
    }

    private Claims extrairTodasClaims(String token) {
        return Jwts.parser()
                .verifyWith(chaveAssinatura)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
