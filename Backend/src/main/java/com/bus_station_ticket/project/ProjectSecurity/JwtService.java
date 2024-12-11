package com.bus_station_ticket.project.ProjectSecurity;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.bus_station_ticket.project.ProjectEntity.AccountEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

       private String secretKey;

       public JwtService() {
              try {
                     KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
                     SecretKey sk = keyGenerator.generateKey();
                     secretKey = Base64.getEncoder().encodeToString(sk.getEncoded());

              } catch (NoSuchAlgorithmException e) {
                     e.printStackTrace();
              }
       }

       private SecretKey getKey() {
              byte[] keyBytes = Decoders.BASE64.decode(secretKey);
              return Keys.hmacShaKeyFor(keyBytes);
       }

       public String token(AccountEntity acc) {

              Map<String, Object> claims = new HashMap<>();

              return Jwts.builder()
                            .claims()
                            .add(claims)
                            .subject(acc.getUserName())
                            .issuedAt(new Date(System.currentTimeMillis()))
                            .expiration(new Date(System.currentTimeMillis() + 86400000))
                            .and()
                            .signWith(getKey())
                            .compact();
       }

       public String extractUserName(String token) {
              // extract username from token
              return extractClaim(token, Claims::getSubject);

       }

       private <T> T extractClaim(String token, Function<Claims, T> claimResolve) {
              final Claims claims = extractAllClaims(token);
              return claimResolve.apply(claims);
       }

       private Claims extractAllClaims(String token) {

              return Jwts.parser()
                     .verifyWith(getKey())
                     .build()
                     .parseSignedClaims(token)
                     .getPayload();
       }

       public boolean validateToken(String token, UserDetails userDetails) {
              final String username = extractUserName(token);
              return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
       }

       private boolean isTokenExpired(String token) {
              return extractExpiration(token).before(new Date());
       }

       private Date extractExpiration(String token) {
              return extractClaim(token, Claims::getExpiration);
       }

}
