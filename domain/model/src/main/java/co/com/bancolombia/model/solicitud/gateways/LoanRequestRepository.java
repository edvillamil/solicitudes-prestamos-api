package co.com.bancolombia.model.solicitud.gateways;

import co.com.bancolombia.model.estado.LoanStatus;
import co.com.bancolombia.model.solicitud.LoanRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Set;

public interface LoanRequestRepository {

    public Mono<LoanRequest> save(LoanRequest loanRequest);

    Flux<LoanRequest> findByEstadosIn(Set<LoanStatus> estados, String email, int page, int size);

    Flux<LoanRequest> findByStatusesInAndEmail(Set<LoanStatus> estados, String email, int page, int size);

}
