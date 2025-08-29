package co.com.bancolombia.model.solicitud.gateways;

import co.com.bancolombia.model.solicitud.LoanRequest;
import reactor.core.publisher.Mono;

public interface LoanRequestRepository {

    public Mono<LoanRequest> save(LoanRequest loanRequest);
}
