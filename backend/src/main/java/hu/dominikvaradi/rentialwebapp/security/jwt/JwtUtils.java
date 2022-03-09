package hu.dominikvaradi.rentialwebapp.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${rentialwebapp.app.jwtToken}")
    private String JwtServerToken;

    @Value("${rentialwebapp.app.jwtExpirationInMillis}")
    private int JwtExpirationInMillis;

    public String generateJwtTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JwtExpirationInMillis))
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(JwtServerToken.getBytes());
    }

    public boolean validateJwtToken(String authToken) {
        try {
            JwtParser parser = Jwts.parserBuilder().setSigningKey(getKey()).build();

            parser.parseClaimsJws(authToken);
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public String getUsernameFromJwtToken(String jwtToken) {
        JwtParser parser = Jwts.parserBuilder().setSigningKey(getKey()).build();

        return parser.parseClaimsJws(jwtToken).getBody().getSubject();
    }
}
