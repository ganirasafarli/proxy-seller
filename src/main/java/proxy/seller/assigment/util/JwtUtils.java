package proxy.seller.assigment.util;

import io.jsonwebtoken.Claims;
import proxy.seller.assigment.domain.dto.UserDTO;

import java.util.Date;
import java.util.Map;

public interface JwtUtils {
    void init();

    Claims getAllClaimsFromToken(String token);

    String getUsernameFromToken(String token);

    String getUserIdFromToken(String token);

    Date getExpirationDateFromToken(String token);

    boolean isTokenExpired(String token);

    String generateToken(UserDTO userDTO);

    String doGenerateToken(Map<String, Object> claims, String username);

    boolean validateToken(String token);
}