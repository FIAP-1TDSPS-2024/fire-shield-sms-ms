package br.com.catech.fire_shield_sms_ms.framework.security;

import br.com.catech.fire_shield_sms_ms.application.core.entities.TokenAcesso;
import br.com.catech.fire_shield_sms_ms.application.core.entities.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final List<String> DEFAULT_AUTHORITIES = List.of("ROLE_USER");

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.issuer:fire-shield-sms-ms}")
    private String issuer;

    @Value("${jwt.expiration-minutes:60}")
    private long expirationMinutes;

    private SecretKey signingKey;

    @PostConstruct
    void init() {
        this.signingKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public TokenAcesso gerarToken(Usuario usuario) {
        Instant now = Instant.now();
        Instant expiresAt = now.plusSeconds(expirationMinutes * 60);

        String token = Jwts.builder()
                .issuer(issuer)
                .subject(usuario.getUsername())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiresAt))
                .claim("uuid", usuario.getUuid().toString())
                .claim("nome", usuario.getNome())
                .claim("roles", DEFAULT_AUTHORITIES)
                .signWith(signingKey)
                .compact();

        return new TokenAcesso(token, expiresAt);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public List<SimpleGrantedAuthority> extractAuthorities(String token) {
        Claims claims = extractAllClaims(token);
        Object rolesClaim = claims.get("roles");

        if (!(rolesClaim instanceof List<?> roles) || roles.isEmpty()) {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return roles.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(extractAllClaims(token));
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
