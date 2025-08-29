package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.estado.LoanStatus;
import co.com.bancolombia.model.tipoprestamo.LoanType;
import co.com.bancolombia.model.tipoprestamo.gateways.LoanTypeRepository;
import co.com.bancolombia.r2dbc.entities.LoanTypeEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public class LoanTypeReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        LoanType,
        LoanTypeEntity,
        UUID,
        TipoPrestamoReactiveRepository
> implements LoanTypeRepository {
    public LoanTypeReactiveRepositoryAdapter(TipoPrestamoReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, LoanType.class));
    }

    @Override
    public Mono<LoanStatus> findByName(String name) {
        return repository.findByName(name);
    }
}
