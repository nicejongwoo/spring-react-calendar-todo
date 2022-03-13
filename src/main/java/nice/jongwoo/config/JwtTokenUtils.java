package nice.jongwoo.config;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import nice.jongwoo.member.Member;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class JwtTokenUtils {

    private final String jwtSecret = "password";
    private final String jwtIssuer = "nicejongwoo.me";

    public String generateAccessToken(Member member) {
        return Jwts.builder()
            .setSubject(String.format("%s,%s,%s", member.getEmail(), member.getUserToken(), member.getUserName())) //payload sub
            .setIssuer(jwtIssuer) //payload iss
            .setIssuedAt(new Date()) //payload iat
            .setExpiration(new Date(System.currentTimeMillis() + 60*60*1000)) //payload exp: 1 hour
            .signWith(SignatureAlgorithm.HS512, jwtSecret) //header sign
            .compact();
    }

    public String generateRefreshToken() {
        return Jwts.builder()
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + 7*24*60*60*1000)) //payload exp: 1 week
            .signWith(SignatureAlgorithm.HS512, jwtSecret) //header sign
            .compact();
    }

    public String getEmail(String token) {
        Claims claims = Jwts.parser().
            setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject().split(",")[0];
    }

    public String getUserToken(String token) {
        Claims claims = Jwts.parser().
            setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject().split(",")[1];
    }

    public String getUserName(String token) {
        Claims claims = Jwts.parser().
            setSigningKey(jwtSecret)
            .parseClaimsJws(token)
            .getBody();
        return claims.getSubject().split(",")[2];
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex) {
            log.error("Invalid JWT signature - {}", ex.getMessage());
        }catch (MalformedJwtException ex) {
            log.error("Invalid JWT token - {}", ex.getMessage());
        }catch (ExpiredJwtException ex) {
            log.error("Expired JWT token - {}", ex.getMessage());
        }catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token - {}", ex.getMessage());
        }catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty - {}", ex.getMessage());
        }

        return false;
    }
}
