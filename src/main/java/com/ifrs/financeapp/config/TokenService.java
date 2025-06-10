package com.ifrs.financeapp.config;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ifrs.financeapp.model.user.User;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secretKey;

    public String generateToken(User user) {
        try {
            Algorithm algoritm = Algorithm.HMAC256(secretKey);
            String token = JWT.create()
                    .withIssuer("jabuti")
                    .withSubject(user.getLogin())
                    .withClaim("name", user.getCompleteName())
                    .withIssuedAt(new Date())
                    .withExpiresAt(generateExpirationDate())
                    .sign(algoritm);

            return token;

        } catch (JWTCreationException e) {
            throw new RuntimeException("Error while generating token", e);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algoritm = Algorithm.HMAC256(secretKey);

            return JWT.require(algoritm)
                    .withIssuer("jabuti")
                    .build()
                    .verify(token)
                    .getSubject();

        } catch (JWTVerificationException e) {
            return "";
        }
    }

    private Instant generateExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
