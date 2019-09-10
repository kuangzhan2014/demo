package com.maitianer.demo.api.security;

import com.maitianer.demo.common.utils.lang.DateUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

/**
 * JWT Token 提供器
 *
 * @Author: Mr.Zhang
 * @Date: 2018-09-19 10:22
 */
@Component
public class JWTTokenProvider {

    private final Logger log = LoggerFactory.getLogger(JWTTokenProvider.class);

    private static final String PHONE_KEY = "phone";

    private Key key;

    private long tokenValidityInMilliseconds;

    private long tokenValidityInMillisecondsForRememberMe;

    @PostConstruct
    public void init() {
        byte[] keyBytes;
        keyBytes = Decoders.BASE64.decode("cElWTHpjZ3E2NWdCSGp3MUdGV0hHcUdqM0gxclZJQUltQk1EQjBEaVBLOTZkNE1LZGtwYzI3ejBxNjFzM1hHOTBxckxYNjNlbWtIOUpKNlU5V0NmZFN1NjAyWU9tUHoxNWdpQ28xa3YzUENrWjVqQmJUNzRIdEd6ZW9ER215cFlBVWplZ1pBeERMRkJSZFdqMGp1N2FMTXkweXN0dzJoNjZPb3JlODFCaHZmb0lHVjdwaU9pSjQ5bk9HWDUwcjVYWExaTGVkdWJkSVlKUkJRUW8yZ1dPZk1KNERjYjR2OU82MEtJQ1pnU0ViZzRoVVEyOFF3bWkzZ0RCN1dzcDFGZw==");
        this.key = Keys.hmacShaKeyFor(keyBytes);
        tokenValidityInMilliseconds = 1800; // 0.5 hour
        tokenValidityInMillisecondsForRememberMe = 259200000; // 3000 hours;
    }

    public String createToken(long userId, String phone, boolean rememberMe) {
        Date now = new Date();
        Date validity;
        if (rememberMe) {
            validity = DateUtils.addDays(now, 365);
        } else {
            validity = DateUtils.addDays(now, 1);
        }

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .claim(PHONE_KEY, phone)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    public String getSubject(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public Object getCustomParam(String token, String paramName) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody().get(paramName);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
            log.trace("Invalid JWT signature trace: {}", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            log.trace("Expired JWT token trace: {}", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            log.trace("Unsupported JWT token trace: {}", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            log.trace("JWT token compact of handler are invalid trace: {}", e);
        }
        return false;
    }
}
