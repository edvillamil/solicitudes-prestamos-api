package co.com.bancolombia.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

@Configuration
public class TokenRelayConfig {

    public static final String CTX_AUTH = "AUTHORIZATION_HEADER";

    // 1) Captura el Authorization del request entrante y lo guarda en el Reactor Context
    @Bean
    public WebFilter tokenContextWebFilter() {
        return (exchange, chain) -> {
            String auth = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            return chain.filter(exchange)
                    .contextWrite(ctx -> (auth == null || auth.isBlank()) ? ctx : ctx.put(CTX_AUTH, auth));
        };
    }
}
