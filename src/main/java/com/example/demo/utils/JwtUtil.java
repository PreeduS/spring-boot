package com.example.demo.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
    private String SECRET_KEY = "secret";
    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    public void setSecret(String secret){
        this.SECRET_KEY = secret;
    }
    public void setSignatureAlgorithm(SignatureAlgorithm signatureAlgorithm){
        this.signatureAlgorithm = signatureAlgorithm;
    }

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
    // Claims are a JWT's 'body'
    private Claims extractAllClaims(String token){
        return Jwts
        .parser()
        .setSigningKey(SECRET_KEY)
        .parseClaimsJws(token)      // throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException;        // base JwtException 
        .getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts
        .builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
        .signWith(signatureAlgorithm, SECRET_KEY)
        .compact();
        

    }

    public Boolean validateToken(String token){
        try{
            extractAllClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }



}