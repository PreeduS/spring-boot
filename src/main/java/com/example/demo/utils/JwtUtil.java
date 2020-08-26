package com.example.demo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.util.FileCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.KeyFactory;

@Service
public class JwtUtil {
    @Value("classpath:private/key.pem")
    private Resource SECRET_KEY;
    @Value("classpath:private/public.pem")
    private Resource PUB_KEY;

    @Autowired
    FileUtil fileUtil;

    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS512; // SignatureAlgorithm.HS256;

    // KeyPair keyPair = Keys.keyPairFor(SignatureAlgorithm.RS512);
    // KeyPair keyPair = new
    // KeyPair(base64Encode(fileUtil.readResourceAsString(PUB_KEY)),
    // base64Encode(fileUtil.readResourceAsString(SECRET_KEY)));
    // private PrivateKey privateKey;
    // private PublicKey publicKey;

    private String base64Encode(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes());

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
    private Claims extractAllClaims(String token) {
        String key = base64Encode(fileUtil.readResourceAsString(PUB_KEY));
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token) // throws ExpiredJwtException,
                                                                      // UnsupportedJwtException, MalformedJwtException,
                                                                      // SignatureException, IllegalArgumentException;
                                                                      // // base JwtException
                .getBody();
    }

    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        // String key = base64Encode(fileUtil.readResourceAsString(SECRET_KEY));
        String key = fileUtil.readResourceAsString(SECRET_KEY);
        // strip the headers
        //key = key.replace("-----BEGIN RSA PRIVATE KEY-----", "");
        //key = key.replace("-----END RSA PRIVATE KEY-----", "");
        //key = key.replaceAll("\\s+", "");
        // PrivateKey privateKey = new PrivateKeyImpl(key.getBytes());

        PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(key.getBytes());
      
        PrivateKey privateKey = null;
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            privateKey = kf.generatePrivate(ks);
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }catch (InvalidKeySpecException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
 
        return Jwts
        .builder()
        .setClaims(claims)
        .setSubject(subject)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
        //.signWith(signatureAlgorithm, key)
        .signWith(privateKey)
        
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