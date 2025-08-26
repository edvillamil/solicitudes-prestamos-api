package co.com.bancolombia.model.tipoprestamo.gateways;

import co.com.bancolombia.model.tipoprestamo.TipoPrestamo;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TipoPrestamoRepository {
    Mono<TipoPrestamo> findById(UUID id);
}
