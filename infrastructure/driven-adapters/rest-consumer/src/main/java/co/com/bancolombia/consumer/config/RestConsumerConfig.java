package co.com.bancolombia.consumer.config;

import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import static io.netty.channel.ChannelOption.CONNECT_TIMEOUT_MILLIS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Configuration
public class RestConsumerConfig {

    private final String url;

    private final int timeout;

    public static final String CTX_AUTH = "AUTHORIZATION_HEADER";

    public RestConsumerConfig(@Value("${adapter.restconsumer.url}") String url,
                              @Value("${adapter.restconsumer.timeout}") int timeout) {
        this.url = url;
        this.timeout = timeout;
    }

    /*@Bean
    public WebClient getWebClient(WebClient.Builder builder) {
        return builder
            .baseUrl(url)
            .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json")
            .clientConnector(getClientHttpConnector())
            .build();
    }*/

    // 2) WebClient hacia el MS de usuarios, que toma el token del Reactor Context y lo reenvía
    @Bean
    public WebClient usuariosClient(WebClient.Builder builder) {
        return builder
                .baseUrl(url)
                .filter((request, next) ->
                        Mono.deferContextual(ctxView -> {
                            String auth = ctxView.hasKey(CTX_AUTH) ? ctxView.get(CTX_AUTH) : null;

                            ClientRequest.Builder mutated = ClientRequest.from(request);
                            if (auth != null && !auth.isBlank()) {
                                // si ya viene con "Bearer " lo respeta; si no, lo agrega
                                if (auth.regionMatches(true, 0, "Bearer ", 0, 7)) {
                                    mutated.headers(h -> h.set(HttpHeaders.AUTHORIZATION, auth));
                                } else {
                                    mutated.headers(h -> h.setBearerAuth(auth));
                                }
                            }
                            return next.exchange(mutated.build());
                        })
                )
                .build();
    }

    private ClientHttpConnector getClientHttpConnector() {
        /*
        IF YO REQUIRE APPEND SSL CERTIFICATE SELF SIGNED: this should be in the default cacerts trustore
        */
        return new ReactorClientHttpConnector(HttpClient.create()
                .compress(true)
                .keepAlive(true)
                .option(CONNECT_TIMEOUT_MILLIS, timeout)
                .doOnConnected(connection -> {
                    connection.addHandlerLast(new ReadTimeoutHandler(timeout, MILLISECONDS));
                    connection.addHandlerLast(new WriteTimeoutHandler(timeout, MILLISECONDS));
                }));
    }

}
