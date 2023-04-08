package proxy.seller.assigment.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import proxy.seller.assigment.domain.model.AuthUserDetails;
import proxy.seller.assigment.util.JwtUtils;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class AuthenticationManager implements ReactiveAuthenticationManager {
    private final JwtUtils jwtUtils;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        try {
            if (!jwtUtils.validateToken(authToken)) {
                return Mono.empty();
            }
            String id = jwtUtils.getUserIdFromToken(authToken);
            AuthUserDetails authUserDetails = new AuthUserDetails();
            authUserDetails.setId(id);
            return Mono.just(new UsernamePasswordAuthenticationToken(authUserDetails, null, null));
        } catch (Exception e) {
            return Mono.empty();
        }
    }
}
