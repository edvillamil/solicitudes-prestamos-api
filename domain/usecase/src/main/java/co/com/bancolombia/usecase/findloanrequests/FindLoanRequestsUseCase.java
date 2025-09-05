package co.com.bancolombia.usecase.findloanrequests;

import co.com.bancolombia.model.estado.LoanStatus;
import co.com.bancolombia.model.estado.gateways.LoanStatusRepository;
import co.com.bancolombia.model.exceptions.StatusNotFoundException;
import co.com.bancolombia.model.solicitud.LoanRequest;
import co.com.bancolombia.model.solicitud.gateways.LoanRequestRepository;
import co.com.bancolombia.model.tipoprestamo.gateways.LoanTypeRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FindLoanRequestsUseCase {

    private final LoanRequestRepository loanRequestRepository;
    private final LoanStatusRepository loanStatusRepository;
    private final LoanTypeRepository loanTypeRepository;

    public Flux<LoanRequest> findLoanRequests(String email, int page, int size, List<String> listStatuses) {

        return Flux.fromIterable(listStatuses)
                .flatMap(loanStatusRepository::findByName)
                .collect(Collectors.toSet())
                .flatMapMany(statuses -> {
                    if (statuses.isEmpty()) {
                        return Flux.error(new StatusNotFoundException("No se encontraron estados en BD"));
                    }

                    return loanRequestRepository.findByStatusesInAndEmail(statuses, email, page, size)
                            .collectList()
                            .flatMapMany(reqs -> {
                                        if (reqs.isEmpty()) return Flux.empty();

                                        List<UUID> statusIdsInPage = reqs.stream()
                                                .map(lr -> lr.getLoanStatus().getId())
                                                .distinct()
                                                .collect(Collectors.toList());

                                        return loanStatusRepository.findAllById(statusIdsInPage)
                                                .collectMap(LoanStatus::getId, s -> s)
                                                .flatMapMany(statusMap ->
                                                        Flux.fromIterable(reqs).map(lr -> {
                                                            LoanStatus full = statusMap.getOrDefault(
                                                                    lr.getLoanStatus().getId(), lr.getLoanStatus()
                                                            );
                                                            lr.setLoanStatus(full); // Ahora incluye el name
                                                            return lr;
                                                        }));
                            });
                }).flatMap(lr -> {
                    UUID loanTypeId = lr.getLoanType().getId();
                    return loanTypeRepository.findById(loanTypeId)
                            .map(fullType -> {
                                lr.setLoanType(fullType); // Ahora incluye el name
                                return lr;
                            });
                });
    }
}
