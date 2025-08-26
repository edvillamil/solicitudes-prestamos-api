package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.tipoprestamo.TipoPrestamo;
import co.com.bancolombia.model.tipoprestamo.gateways.TipoPrestamoRepository;
import co.com.bancolombia.r2dbc.entities.TipoPrestamoEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class TipoPrestamoReactiveRepositoryAdapter extends ReactiveAdapterOperations<
        TipoPrestamo,
        TipoPrestamoEntity,
        UUID,
        TipoPrestamoReactiveRepository
> implements TipoPrestamoRepository {
    public TipoPrestamoReactiveRepositoryAdapter(TipoPrestamoReactiveRepository repository, ObjectMapper mapper) {

        super(repository, mapper, d -> mapper.map(d, TipoPrestamo.class));
    }

}
