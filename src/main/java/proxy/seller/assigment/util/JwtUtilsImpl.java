package proxy.seller.assigment.util;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import proxy.seller.assigment.domain.dto.UserDTO;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtUtilsImpl implements JwtUtils {
    @Value("${app.security.jwt.secret}")
    private String secret;
    @Value("${app.security.jwt.expiration-seconds}")
    private Long expirationSeconds;
    private final TimeUtils timeUtils;

    private Key key;

    @PostConstruct
    @Override
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    @Override
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key)
                .build().parseClaimsJws(token).getBody();
    }

    @Override
    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }

    @Override
    public String getUserIdFromToken(String token) {
        return getAllClaimsFromToken(token).get("userId", String.class);
    }

    @Override
    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    @Override
    public boolean isTokenExpired(String token) {
        final Long nowEpoch = timeUtils.getEpochMillis();
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date(nowEpoch));
    }

    @Override
    public String generateToken(UserDTO userDTO) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userDTO.getId());
        return doGenerateToken(claims, userDTO.getUsername());
    }

    @Override
    public String doGenerateToken(Map<String, Object> claims, String username) {
        final Long nowEpoch = timeUtils.getEpochMillis();
        final Date createdDate = new Date(nowEpoch);
        final Date expirationDate = new Date(nowEpoch + expirationSeconds * 1000);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(key)
                .compact();

    }

    @Override
    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}
