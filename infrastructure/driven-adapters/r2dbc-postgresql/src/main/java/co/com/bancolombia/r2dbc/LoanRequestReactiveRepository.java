package co.com.bancolombia.r2dbc;

import co.com.bancolombia.r2dbc.entities.LoanRequestEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

// TODO: This file is just an example, you should delete or modify it
public interface LoanRequestReactiveRepository
        extends ReactiveCrudRepository<LoanRequestEntity, UUID>, ReactiveQueryByExampleExecutor<LoanRequestEntity> {

    @Query("""
              SELECT * FROM loan_request WHERE status_id IN (:statusIds)
              LIMIT :#{#pageable.pageSize} OFFSET :#{#pageable.offset}
           """)
    Flux<LoanRequestEntity> findByStatusesIn(@Param("statusIds") List<UUID> statuses, Pageable pageable);

    @Query("""
         SELECT * FROM loan_request
         WHERE status_id IN (:statusIds)
         
         LIMIT :#{#pageable.pageSize} OFFSET :#{#pageable.offset}
         """)
    Flux<LoanRequestEntity> findByStatusesInAndEmail(@Param("statusIds") List<UUID> statuses,
                                                     @Param("email") String email,
                                                     Pageable pageable);


}
