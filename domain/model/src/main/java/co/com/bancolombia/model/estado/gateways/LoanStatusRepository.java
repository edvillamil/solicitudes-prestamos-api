package co.com.bancolombia.model.estado.gateways;

import co.com.bancolombia.model.estado.LoanStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

public interface LoanStatusRepository {
    Mono<LoanStatus> findByName(String name);

    Flux<LoanStatus> findAllById(List<UUID> statuses);
}
