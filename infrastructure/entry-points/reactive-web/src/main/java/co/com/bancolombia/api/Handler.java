package co.com.bancolombia.api;

import co.com.bancolombia.api.dto.LoanRequestDto;
import co.com.bancolombia.api.mapper.LoanMapper;
import co.com.bancolombia.model.solicitud.LoanRequest;
import co.com.bancolombia.usecase.registrarsolicitudprestamo.SaveLoanRequestUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class Handler {

    private final SaveLoanRequestUseCase saveLoanRequestUseCase;


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
}
