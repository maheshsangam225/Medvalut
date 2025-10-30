package com.example.demo.Service;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtService {
    @Value("${jwt.secret}")
    private String str;
    private SecretKey secretKey;
    byte[] byteKey;
    @PostConstruct
    public void init() {
        try {
            if(str == null ||  str.isEmpty()) {
                throw new RuntimeException("secret key must be configured");
            }
            byteKey = Base64.getDecoder().decode(str);
            if(byteKey.length < 32) {
                throw new RuntimeException("secret key is too short");

            }
            this.secretKey = Keys.hmacShaKeyFor(byteKey);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public String GenerateToken(Authentication authentication) {
        return Jwts
                .builder()
                .subject(authentication.getName())
                .claim("roles", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000*60*60*10))
                .signWith(secretKey)
                .compact();
    }

    public String getUserNameFromJwt(String token) {
        return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
    public boolean ValidateToken(String token) {
            try {
                Jwts
                        .parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(token);
                return true;

            }
            catch (Exception e) {
                return  false;
            }
        }

}
