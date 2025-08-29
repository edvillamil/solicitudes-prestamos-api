package co.com.bancolombia.consumer;

import co.com.bancolombia.consumer.mapper.UserMapper;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.UUID;

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
                .bodyToMono(UserResponse.class)
                .map(UserMapper::toEntity);
    }

    public Mono<String> fallBackGetUser(String email, Exception ignored) {
        return Mono.just("No se pudo obtener la información del usuario. Por favor, intente más tarde.");
    }
}
