package co.com.bancolombia.api;

import co.com.bancolombia.api.config.JwtProvider;
import co.com.bancolombia.api.dto.LoanRequestDto;
import co.com.bancolombia.api.dto.LoanRequestPageable;
import co.com.bancolombia.api.mapper.LoanMapper;
import co.com.bancolombia.model.solicitud.LoanRequest;
import co.com.bancolombia.usecase.findloanrequests.FindLoanRequestsUseCase;
import co.com.bancolombia.usecase.registrarsolicitudprestamo.SaveLoanRequestUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static co.com.bancolombia.api.config.TokenRelayConfig.CTX_AUTH;

@Component
@RequiredArgsConstructor
public class Handler {

    private final SaveLoanRequestUseCase saveLoanRequestUseCase;
    private final FindLoanRequestsUseCase findLoanRequestsUseCase;
    private final JwtProvider jwtProvider;


    public Mono<ServerResponse> registrarSolicitudPrestamo(ServerRequest serverRequest) {

        Mono<LoanRequestDto> loanRequestDto = serverRequest.bodyToMono(LoanRequestDto.class);

        return loanRequestDto.flatMap(
                loan -> {
                    return saveLoanRequestUseCase.registrar(LoanMapper.toEntity(loan))
                            .flatMap(loanRequest -> ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(BodyInserters.fromValue(loanRequest))
                            );
                }
        );
    }

    public Mono<ServerResponse> obtenerSolicitudesPrestamo(ServerRequest serverRequest) {

        /*Mono<String> emailFromToken = ReactiveSecurityContextHolder.getContext()
                .map(ctx -> (JwtAuthenticationToken) ctx.getAuthentication())
                .map(JwtAuthenticationToken::getToken)
                .map(jwt -> {
                    String email = jwt.getClaimAsString("email");
                    if (email == null) email = jwt.getClaimAsString("preferred_username");
                    if (email == null) email = jwt.getSubject();
                    return email;
                });


        Mono<String> emailFromTokenQuick = Mono.deferContextual(ctx -> {
            String auth = ctx.hasKey(CTX_AUTH) ? ctx.get(CTX_AUTH) : null;
            if (auth == null || auth.isBlank()) return Mono.empty();
            String token = auth.startsWith("Bearer ") ? auth.substring(7) : auth;
            try {
                String email = jwtProvider.extractClaim("email", token);
                if (email == null || email.isBlank()) {
                    email = jwtProvider.extractClaim("preferred_username", token);
                }
                if (email == null || email.isBlank()) {
                    // como fallback usa "sub"
                    email = jwtProvider.extractClaim("sub", token);
                }
                return Mono.justOrEmpty(email);
            } catch (Exception e) {
                return Mono.empty(); // o Mono.error(...) si quieres cortar el flujo
            }
        });

        Mono<String> emailFromToken2 = serverRequest.principal()
                .cast(JwtAuthenticationToken.class)
                .map(jwt -> jwt.getToken())
                .map(jwt -> {
                    String email = jwt.getClaimAsString("email");
                    if (email == null || email.isBlank()) email = jwt.getClaimAsString("preferred_username");
                    if (email == null || email.isBlank()) email = jwt.getSubject(); // sub como fallback
                    return email;
                });

        Mono<String> tokenMono = Mono.justOrEmpty(serverRequest.headers().firstHeader(HttpHeaders.AUTHORIZATION))
                .map(auth -> auth.startsWith("Bearer ") ? auth.substring(7) : auth);
*/

        return serverRequest.bodyToMono(LoanRequestPageable.class)
                .flatMap(dto ->
                        findLoanRequestsUseCase.findLoanRequests(
                                        dto.getEmail(),
                                        dto.getPage(),
                                        dto.getSize(),
                                        dto.getStatuses())
                                .collectList() // Mono<List<LoanRequest>>
                                //mapear al response
                                .flatMap(lista -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(lista))
                );
    }
}
