package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.solicitud.LoanRequest;
import co.com.bancolombia.model.solicitud.gateways.LoanRequestRepository;
import co.com.bancolombia.r2dbc.entities.LoanRequestEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.mapper.SolicitudMapper;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class LoanRequestReactiveRepositoryAdapter
        extends ReactiveAdapterOperations<LoanRequest, LoanRequestEntity, UUID, SolicitudReactiveRepository>
        implements LoanRequestRepository {
    public LoanRequestReactiveRepositoryAdapter(SolicitudReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, LoanRequest.class));
    }

    @Override
    //@Transactional // asegura atomicidad en la operación R2DBC
    public Mono<LoanRequest> save(LoanRequest loanRequest) {
        LoanRequestEntity loanRequestEntity = toData(loanRequest);
        loanRequestEntity.setLoanType(loanRequest.getLoanType().getId());
        loanRequestEntity.setStatusId(loanRequest.getLoanStatus().getId());
        return repository.save(loanRequestEntity).map(SolicitudMapper::toDomain);
    }

}
