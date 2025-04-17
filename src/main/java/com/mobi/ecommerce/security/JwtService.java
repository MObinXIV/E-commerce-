package com.mobi.ecommerce.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class JwtService {

    public String generateToken(UserDetails userDetails){
        return generateToken(new HashMap<>(),userDetails);
    }
//    public String generateToken(String subject,String ...scopes){
//        return generateToken(subject,Map.of("scopes",scopes));
//    }


    private String  SECRET_KEY="HiDdUqdbPOfP+47Zb+8Q3XH2irkoCQsLkN8luunKnmyiSfvE15IjroiNB5RjLvob" ;
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

public <T> T extractClaim (String token, Function<Claims,T> claimResolver){
    // Extracts a specific claim from the JWT token
    final  Claims claims = extractAllClaims(token);
    return claimResolver.apply(claims);

}
private Claims extractAllClaims(String token){
    return Jwts
            .parserBuilder()
            .setSigningKey(getSigningKey()) // Uses the signing key to validate the token
            .build()
            .parseClaimsJws(token) // Parses the JWT and verifies its signature
            .getBody(); // Returns the payload (claims)
}

   public String generateToken (Map<String, Object> extraClaims, UserDetails userDetails){
        var authorities = userDetails.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .toList();
        return Jwts
                .builder()
                .setClaims(extraClaims) // Adds any additional claims (e.g., roles, permissions)
                .setSubject(userDetails.getUsername()) // Sets the subject (usually the username/email)
                .setIssuedAt(Date.from(Instant.now())) // Sets the "issued at" claim to the current time
                .setExpiration(Date.from(Instant.now().plus(15,DAYS)) )
                .claim("authorities",authorities)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256) // Signs the JWT with the secret key
                .compact(); // Compacts the JWT into a compact, URL-safe string
    }
    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);  // ✅ Fixed incorrect method call
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public boolean isTokenExpired(String token) {
        return extractExpirationToken(token).before(new Date());
    }
    private Date extractExpirationToken(String token) {
        // Extracts the expiration time from the JWT token
        return extractClaim(token, Claims::getExpiration);

    }
    private Key getSigningKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
