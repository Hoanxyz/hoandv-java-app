package com.example.firstproject.helper.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtService {
    public static final String SECRET = "MIIC/jCCAeagAwIBAgIBATANBgkqhkiG9w0BAQsFADAtMQswCQYDVQQGEwJHQjEb" +
            "MBkGA1UECAwSTWljaGlnYW4gUEFSS0lORzETMBEGA1UEBwwKQW1zdGVyZGFtMQ0w" +
            "CwYDVQQKDARPbmxpbmUxDTALBgNVBAsMBE9ubGluZTESMBAGA1UEAwwJbG9jYWxo" +
            "ZWFkMB4XDTIxMDUyMjEzMzg0M1oXDTIyMDUyMTEzMzg0M1owLTEbMBkGA1UECAwS" +
            "TWljaGlnYW4gUEFSS0lORzETMBEGA1UEBwwKQW1zdGVyZGFtMQ0wCwYDVQQKDARO" +
            "bmxpbmUxDTALBgNVBAsMBE5ubGluZTESMBAGA1UEAwwJbG9jYWxoZWFkMFowGAYJ" +
            "KoZIhvcNAQkBFgtyZW1haWxAZXhhbXBsZS5jb20wWTATBgcqhkjOPQIBBggqhkjO" +
            "PQMBBwNCAAR8b4Cn3jZqG0oXkUWjF0UgqV7qW6J7VpJXbQ8nXNzJFJ3QlZqxYJzS" +
            "S3qHj4B8rA7DcmJTJtUfBZ7xJiJ2Kq2uo4IC9jCCAv4wHwYDVR0jBBgwFoAU2XfK" +
            "8IlzjtT4mOPqg1tXjQOWDzkwHQYDVR0OBBYEFKqkZ4qZ8/6qT4D9dEFBICx7PYgJ" +
            "MB8GA1UdIwQYMBaAFKqkZ4qZ8/6qT4D9dEFBICx7PYgJMA8GA1UdEwEB/wQFMAMB" +
            "Af8wCgYIKoZIzj0EAwIDSQAwRgIhAK9T4mI0R6D2kp9Y2LlJHt3STx6uBd4MFQG5" +
            "RrmPdSiDAiEAhGq9L9qur5QOqvZDqYjLb0fWAmn6G3JFj9u5OgDvK7E=";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        String a = "123";
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        String a = "123";
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
