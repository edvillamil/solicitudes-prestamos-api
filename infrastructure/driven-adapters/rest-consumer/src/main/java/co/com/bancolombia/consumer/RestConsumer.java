package co.com.bancolombia.consumer;

import co.com.bancolombia.consumer.dto.ClientError;
import co.com.bancolombia.consumer.exceptions.RemoteServiceException;
import co.com.bancolombia.consumer.mapper.UserMapper;
import co.com.bancolombia.model.exceptions.UserNotFoundException;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.naming.ServiceUnavailableException;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestConsumer implements UserRepository {

    private final WebClient client;

    @CircuitBreaker(name = "findByEmail" , fallbackMethod = "fallBackGetUser")
    public Mono<User> findByEmail(String email) {


        return client
                .get()
                .uri("/{email}", Map.of("email", email))
                .retrieve()
                //.bodyToMono(UserResponse.class)
                //.map(UserMapper::toEntity);
                //.onStatus(HttpStatusCode::is4xxClientError, this::handle4xxClientError)
                //.onStatus(HttpStatusCode::is5xxServerError, this::handle5xxServerError)
                .bodyToMono(UserResponse.class)
                .map(UserMapper::toEntity);
    }

    private Mono<User> fallBackGetUser(String email, String bearerToken, Throwable ex) {
        log.error("Fallback findByEmail -> email={}, causa={} - {}",
                email, ex.getClass().getSimpleName(), ex.getMessage(), ex);

        // 1) Si el circuito está abierto
        if (ex instanceof CallNotPermittedException) {
            return Mono.error(new RemoteServiceException("Servicio de usuarios no disponible (circuito abierto).", ex));
        }

        return Mono.error(new RemoteServiceException("Fallo consultando usuario: " + ex.getMessage(), ex));
    }

    private Mono<? extends Throwable> handle4xxClientError(ClientResponse clientResponse) {
        return clientResponse.bodyToMono(ClientError.class)
                .flatMap(errorClient -> {

                    HttpStatusCode status = clientResponse.statusCode();

                    if (status == HttpStatus.NOT_FOUND) {
                        return Mono.error(new UserNotFoundException(errorClient.detail()));
                    } else if (status == HttpStatus.BAD_REQUEST) {
                        return Mono.error(new IllegalArgumentException(errorClient.detail()));
                    } else {
                        return Mono.error(new RuntimeException(errorClient.detail()));
                    }
                });
    }

    private Mono<? extends Throwable> handle5xxServerError(ClientResponse clientResponse) {
        return clientResponse.bodyToMono(ClientError.class)
                .flatMap(errorClient -> {

                    HttpStatusCode status = clientResponse.statusCode();
                    if (status == HttpStatus.SERVICE_UNAVAILABLE) {
                        return Mono.error(new ServiceUnavailableException(errorClient.detail()));
                    }
                    return Mono.error(new RuntimeException(errorClient.detail()));
                });
    }
}
