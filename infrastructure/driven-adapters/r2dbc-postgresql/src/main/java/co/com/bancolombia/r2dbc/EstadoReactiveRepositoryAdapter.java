package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.estado.Estado;
import co.com.bancolombia.model.estado.gateways.EstadoRepository;
import co.com.bancolombia.r2dbc.entities.EstadoEntity;
import co.com.bancolombia.r2dbc.helper.ReactiveAdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import javax.swing.text.html.parser.Entity;
import java.util.UUID;

@Repository
public class EstadoReactiveRepositoryAdapter
        extends ReactiveAdapterOperations<Estado, EstadoEntity, UUID, EstadoReactiveRepository>
        implements EstadoRepository {

    public EstadoReactiveRepositoryAdapter(EstadoReactiveRepository repository, ObjectMapper mapper) {
        super(repository, mapper, d -> mapper.map(d, Estado.class));
    }

    @Override
    public Mono<Estado> findByName(String nombre) {
        return repository.findByName(nombre);
    }
}
