package co.com.bancolombia.api.security;

import co.com.bancolombia.model.auth.gateways.AuthJwtGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements ReactiveAuthenticationManager {

    private final AuthJwtGateway jwtGateway;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String token = authentication.getCredentials().toString();

        if (!jwtGateway.validate(token)) {
            return Mono.empty();
        }

        String email = jwtGateway.extractClaim("email", token);
        String role = jwtGateway.extractClaim("rol", token);

        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));

        AbstractAuthenticationToken auth = new JwtAuthenticationToken(email, token, authorities);
        return Mono.just(auth);
    }
}
