package co.com.bancolombia.r2dbc;

import co.com.bancolombia.model.estado.LoanStatus;
import co.com.bancolombia.r2dbc.entities.LoanStatusEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

// TODO: This file is just an example, you should delete or modify it
public interface LoanStatusReactiveRepository
        extends ReactiveCrudRepository<LoanStatusEntity, UUID>, ReactiveQueryByExampleExecutor<LoanStatusEntity> {

    Mono<LoanStatus> findByName(String name);

    @Query("""
         SELECT * FROM loan_status
         WHERE id IN (:statuses)
         """)
    Flux<LoanStatus> findAllById(List<UUID> statuses);
}
