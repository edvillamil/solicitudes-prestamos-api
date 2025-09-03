package co.com.bancolombia.usecase.findloanrequests;

import co.com.bancolombia.model.estado.LoanStatus;
import co.com.bancolombia.model.estado.gateways.LoanStatusRepository;
import co.com.bancolombia.model.solicitud.LoanRequest;
import co.com.bancolombia.model.solicitud.gateways.LoanRequestRepository;
import co.com.bancolombia.model.tipoprestamo.LoanType;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FindLoanRequestsUseCase {

    private final LoanRequestRepository loanRequestRepository;
    private final LoanStatusRepository loanStatusRepository;

    public Flux<LoanRequest> findLoanRequests(String email, int page, int size) {

        String PENDING_REVIEW = "PENDING_REVIEW";
        String REJECTED = "REJECTED";
        String MANUAL_REVIEW = "MANUAL_REVIEW";

        List<String> names = List.of(PENDING_REVIEW, REJECTED, MANUAL_REVIEW);

        return Flux.fromIterable(names)
                .flatMap(loanStatusRepository::findByName)
                .collect(Collectors.toSet())
                .flatMapMany( statuses -> {
                    if (statuses.isEmpty()) {
                        return Flux.error(new IllegalStateException("No se encontraron estados en BD"));
                    }

                    return loanRequestRepository.findByStatusesInAndEmail(statuses, email, page, size);
                })/*.collectList()
                .flatMapMany(reqs -> {
                    if (reqs.isEmpty()) return Flux.empty();

                    List<UUID> statusIdsInPage = reqs.stream()
                            .map(lr -> lr.getLoanStatus().getId())
                            .collect(Collectors.toList());

                    return loanStatusRepository.findAllById(statusIdsInPage)
                            .collectMap(LoanStatus::getId, s -> s)                   // Map<UUID, LoanStatus>
                            .flatMapMany(statusMap ->
                                    Flux.fromIterable(reqs).map(lr -> {
                                        LoanStatus full = statusMap.getOrDefault(
                                                lr.getLoanStatus().getId(), lr.getLoanStatus()
                                        );
                                        lr.setLoanStatus(full);                          // o builder/@With si es inmutable
                                        return lr;
                                    })
                            );
                })*/;
    }
}
