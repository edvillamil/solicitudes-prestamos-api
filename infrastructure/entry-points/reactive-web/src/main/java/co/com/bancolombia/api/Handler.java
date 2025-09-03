package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.LoanRequestDto;
import co.com.bancolombia.api.dto.LoanRequestPageable;
import co.com.bancolombia.api.mapper.LoanMapper;
import co.com.bancolombia.model.solicitud.LoanRequest;
import co.com.bancolombia.usecase.findloanrequests.FindLoanRequestsUseCase;
import co.com.bancolombia.usecase.registrarsolicitudprestamo.SaveLoanRequestUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Handler {

    private final SaveLoanRequestUseCase saveLoanRequestUseCase;
    private final FindLoanRequestsUseCase findLoanRequestsUseCase;


    public Mono<ServerResponse> registrarSolicitudPrestamo(ServerRequest serverRequest) {

        Mono<LoanRequestDto> loanRequestDto = serverRequest.bodyToMono(LoanRequestDto.class);

        return loanRequestDto.flatMap(
                loan -> {
                    return saveLoanRequestUseCase.registrar(LoanMapper.toEntity(loan))
                            .flatMap(loanRequest -> ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(BodyInserters.fromValue(loanRequest))
                            );
                }
        );
    }

    public Mono<ServerResponse> obtenerSolicitudesPrestamo(ServerRequest serverRequest) {

        return serverRequest.bodyToMono(LoanRequestPageable.class)
                .flatMap(dto ->
                        findLoanRequestsUseCase.findLoanRequests(dto.getEmail(), dto.getPage(), dto.getSize())
                                .collectList() // Mono<List<LoanRequest>>
                                .flatMap(lista -> ServerResponse.ok()
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .bodyValue(lista))
                );
    }
}
