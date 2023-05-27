package com.hakimbooks.hakimbooks.utility;

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
public class JwtHelper {
    private static final String secret="26452948404D635166546A576D5A7134743777217A25432A462D4A614E645267";
    private Key getSinginKey(){
        byte[] bytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(bytes);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSinginKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    private <T> T extractClaim(String token, Function<Claims,T> parseResolver){
        return parseResolver.apply(extractAllClaims(token));
    }
    public String extractUsername(String token){
        return extractClaim(token,Claims::getSubject);
    }
    public Date extractExpiration(String token){
        return extractClaim(token,Claims::getExpiration);
    }
    private boolean isExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    private boolean isValidToken(String token, UserDetails userDetails){
        return extractUsername(token).equalsIgnoreCase(userDetails.getUsername()) && !isExpired(token);
    }
    public String generateToken(UserDetails userDetails){
        Map<String,Object> extraClaims=new HashMap<>();
        return generateToken(extraClaims,userDetails);
    }
    public String generateToken(Map<String,Object> claims,UserDetails userDetails){
        return Jwts.builder()
                .setClaims(claims)
                .signWith(getSinginKey(), SignatureAlgorithm.HS256)
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 7)))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setSubject(userDetails.getUsername())
                .compact();
    }
}
