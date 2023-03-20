package ru.bendricks.employeeadministratoion.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.stereotype.Component;
import ru.bendricks.employeeadministratoion.model.User;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String secret;

    public String generateToken(User user){
        return JWT.create().withSubject("user_details")
                .withClaim("id", user.getId())
                .withClaim("role", user.getRole())
                .withIssuedAt(new Date())
                .withIssuer("Employee_Administration_Server")
                .withExpiresAt(
                        Date.from(ZonedDateTime.now().plusMinutes(120).toInstant())
                ).sign(Algorithm.HMAC512(secret));
    }

    public int validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        DecodedJWT jwt = JWT.require(Algorithm.HMAC512(secret))
                .withSubject("user_details")
                .withIssuer("Employee_Administration_Server")
                .build().verify(token);
        return jwt.getClaim("id").asInt();
    }

}
