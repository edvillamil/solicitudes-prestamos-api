package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.estado.LoanStatus;
import co.com.bancolombia.model.estado.gateways.LoanStatusRepository;
import co.com.bancolombia.r2dbc.entities.LoanStatusEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import co.com.bancolombia.r2dbc.mapper.EstadoMapper;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Repository
public class LoanStatusReactiveRepositoryAdapter
        extends ReactiveAdapterOperations<LoanStatus, LoanStatusEntity, UUID, LoanStatusReactiveRepository>
        implements LoanStatusRepository {

    public LoanStatusReactiveRepositoryAdapter(LoanStatusReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, LoanStatus.class));
    }

    @Override
    public Mono<LoanStatus> findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public Flux<LoanStatus> findAllById(List<UUID> statuses) {
        return repository.findAllById(statuses);
    }

}
