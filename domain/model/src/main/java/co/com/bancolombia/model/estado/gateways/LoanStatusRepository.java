package co.com.bancolombia.model.estado.gateways;

import co.com.bancolombia.model.estado.LoanStatus;
import reactor.core.publisher.Mono;

public interface LoanStatusRepository {
    Mono<LoanStatus> findByName(String name);
}
