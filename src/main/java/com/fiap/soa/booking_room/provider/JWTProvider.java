package com.fiap.soa.booking_room.provider;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fiap.soa.booking_room.infrastructure.excpetion.BadCredentialsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTProvider {

    @Value("${api.security.token.secret}")
    private String secret;

    public String validateToken(String token) throws BadCredentialsException {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        try{
            return JWT.require(algorithm).build().verify(token).getSubject();

        }catch (JWTVerificationException e){
            throw new BadCredentialsException("INVALID_TOKEN");
        }


    }
}
