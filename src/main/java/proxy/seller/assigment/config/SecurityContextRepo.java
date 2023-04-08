package proxy.seller.assigment.config;

import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import proxy.seller.assigment.constants.Constants;
import proxy.seller.assigment.service.AuthenticationManager;
import reactor.core.publisher.Mono;

@Component("securityContextRepo")
@RequiredArgsConstructor
public class SecurityContextRepo implements ServerSecurityContextRepository {
    private final AuthenticationManager authenticationManagerService;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        throw new UnsupportedOperationException("This method is not supported.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        ServerHttpRequest request = exchange.getRequest();
        String authHeader = request.getHeaders().getFirst(Constants.HEADER_KEY_AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith(Constants.HEADER_VALUE_AUTHORIZATION_PREFIX)) {
            String authToken = authHeader.substring(Constants.HEADER_VALUE_AUTHORIZATION_PREFIX.length());
            Authentication auth = new UsernamePasswordAuthenticationToken(authToken, authToken);
            return authenticationManagerService.authenticate(auth).map(SecurityContextImpl::new);
        } else {
            return Mono.empty();
        }
    }
}
