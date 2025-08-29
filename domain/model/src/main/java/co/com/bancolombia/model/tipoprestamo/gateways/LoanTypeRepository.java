package co.com.bancolombia.model.tipoprestamo.gateways;

import co.com.bancolombia.model.estado.LoanStatus;
import co.com.bancolombia.model.tipoprestamo.LoanType;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface LoanTypeRepository {
    Mono<LoanType> findById(UUID id);

    Mono<LoanStatus> findByName(String name);
}
