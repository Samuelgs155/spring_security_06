package com.spring.security.app_security.security.jwt;

import io.jsonwebtoken.Jwts;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    public static final long JWT_TOKEN_VALIDITY_SECONDS = 5 * 60 * 60;
    public static final String JWT_SECRET = "jxgEQe.XHuPq8VdbyYFNkAN.dudQ0903YUn4";

    private Claims getAllClaimsFromToken(String token) {
        final var key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
        final var claims =  getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token){
        final var expirationDate = this.getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }

    public String getUsernameFromToken(String token) {
        return this.getClaimsFromToken(token, Claims::getSubject);
    }

    public String generateToken(UserDetails userDetails) {
        final Map<String, Object> claims = Collections.singletonMap("ROLES", userDetails.getAuthorities().toString());
        return this.getToken(claims, userDetails.getUsername());
    }

    private String getToken(Map<String,Object> claims, String subject) {
        final var key = Keys.hmacShaKeyFor(JWT_SECRET.getBytes(StandardCharsets.UTF_8));
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY_SECONDS * 1000))
                .signWith(key)
                .compact();

    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final var usernameFromUserDetails = userDetails.getUsername();
        final var usernameFromJwt = this.getUsernameFromToken(token);
        return usernameFromUserDetails.equals(usernameFromJwt) && !this.isTokenExpired(token);
    }


}
