package com.ku.gateway.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

import static java.lang.String.format;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${spring.security.jwt.issuer}")
    private String issuer;

    private long expiration;

    private Clock clock;

    @Value("${spring.security.jwt.expiration}")
    public void setExpiration(Duration duration) {
        this.expiration = duration.toMillis();
    }

    @Autowired
    public void setClock(Clock clock) {
        this.clock = clock;
    }

    private final Key secret = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    public String generateAccessToken(JwtUser user) {
        LocalDateTime ldt = LocalDateTime.now(clock).plusSeconds(expiration);
        return "Bearer " + Jwts.builder()
                .setSubject(format("%s,%s", user.getId(), user.getUsername()))
                .setIssuer(issuer)
                .setIssuedAt(new Date())
                .setExpiration(Date.from(ldt.atZone(ZoneId.ofOffset("UTC", ZoneOffset.UTC)).toInstant()))
                .signWith(secret)
                .compact();
    }

    public String getUsername(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject().split(",")[1];
    }

    public boolean validate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        }
        return false;
    }
}