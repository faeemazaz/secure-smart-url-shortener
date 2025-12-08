package com.url.shortner.secure_smart_url_shortener.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtill {

    // secret key passed from application.properties
    @Value("${jwt.secret}")
    private String jwtSecret;

    // token expiry time passed from application.properties
    @Value("${jwt.expiration}")
    private int jwtExpirationMS;

    // Key object used for signing the JWT
    private SecretKey key;

    // Initialize the secret key after bean creation
    @PostConstruct
    public void init() {
        key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    // generate jwt token
    public String generateToken(UserDetails user) {
        Date now = new Date();
        Date expiryData = new Date(now.getTime() + jwtExpirationMS);
        String token = Jwts.builder()
                // Payload
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryData)
                // Signature
                .signWith(key, SignatureAlgorithm.HS256)
                // Header
                .compact();
        return token;
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key) // verify signature
                .build()
                .parseClaimsJws(token)// fetch header + payload + signature
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String token, UserDetails user) {
        // get username from payload
        String username = extractUsername(token);

        // check username getting from extracted token  is same as a username passed by user
        return username.equals(user.getUsername());
    }
}
