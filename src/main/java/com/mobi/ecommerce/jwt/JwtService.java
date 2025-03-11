package com.mobi.ecommerce.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class JwtService {

    public String generateToken(String subject){
        return generateToken(subject,Map.of());
    }
    public String generateToken(String subject,String ...scopes){
        return generateToken(subject,Map.of("scopes",scopes));
    }
    private static final String SECRET_KEY = "HiDdUqdbPOfP+47Zb+8Q3XH2irkoCQsLkN8luunKnmyiSfvE15IjroiNB5RjLvob";
    public String generateToken(String subject, Map<String,Object> claims){


        String token= Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(
                        Date.from(
                                Instant.now().plus(15,DAYS)
                        )
                )
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
        return  token;
    }

    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
