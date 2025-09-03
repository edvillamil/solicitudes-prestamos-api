package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.estado.LoanStatus;
import co.com.bancolombia.model.solicitud.LoanRequest;
import co.com.bancolombia.model.solicitud.gateways.LoanRequestRepository;
import co.com.bancolombia.r2dbc.entities.LoanRequestEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.mapper.SolicitudMapper;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class LoanRequestReactiveRepositoryAdapter
        extends ReactiveAdapterOperations<LoanRequest, LoanRequestEntity, UUID, LoanRequestReactiveRepository>
        implements LoanRequestRepository {
    public LoanRequestReactiveRepositoryAdapter(LoanRequestReactiveRepository repository, ObjectMapper mapper) {
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

    @Override
    public Flux<LoanRequest> findByEstadosIn(Set<LoanStatus> statuses, String email, int page, int size) {
        List<UUID> statusesCodes = statuses.stream().map(LoanStatus::getId).collect(Collectors.toList());

        return repository
                .findByStatusesIn(statusesCodes, PageRequest.of(page, size))
                .map(SolicitudMapper::toDomain);
    }

    @Override
    public Flux<LoanRequest> findByStatusesInAndEmail(Set<LoanStatus> statuses, String email, int page, int size) {

        List<UUID> statusesCodes = statuses.stream().map(LoanStatus::getId).collect(Collectors.toList());

        return repository
                .findByStatusesInAndEmail(statusesCodes, email, PageRequest.of(page, size))
                .map(SolicitudMapper::toDomain);
    }

}
