package co.com.bancolombia.usecase.registrarsolicitudprestamo;

import co.com.bancolombia.model.estado.LoanStatus;
import co.com.bancolombia.model.estado.gateways.LoanStatusRepository;
import co.com.bancolombia.model.solicitud.LoanRequest;
import co.com.bancolombia.model.solicitud.gateways.LoanRequestRepository;
import co.com.bancolombia.model.tipoprestamo.LoanType;
import co.com.bancolombia.model.tipoprestamo.gateways.LoanTypeRepository;
import co.com.bancolombia.model.user.User;
import co.com.bancolombia.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class SaveLoanRequestUseCase {

    private final LoanRequestRepository loanRequestRepository;
    private final LoanTypeRepository loanTypeRepository;
    private final LoanStatusRepository loanStatusRepository;
    private final UserRepository userRepository;

    private final String PENDING_REVIEW = "PENDING_REVIEW";

    public Mono<LoanRequest> registrar(LoanRequest loanRequest) {

        Mono<User> userMono = userRepository.findByEmail(loanRequest.getEmail())
                .switchIfEmpty(Mono.error(
                        new IllegalArgumentException(
                                String.format("El cliente con email %s no existe", loanRequest.getEmail())
                        )
                ));

        return loanTypeRepository.findById(loanRequest.getLoanType().getId())
                        .switchIfEmpty(Mono.error(new IllegalArgumentException("Tipo de préstamo no existe")))
                        .flatMap(type ->
                                // Obtener el estado inicial
                                loanStatusRepository.findByName(PENDING_REVIEW)
                                        .switchIfEmpty(Mono.error(new IllegalStateException("Estado inicial no encontrado")))
                                        .flatMap(status -> {
                                            // Asignar tipo de préstamo y estado a la solicitud
                                            loanRequest.setLoanType(type);
                                            loanRequest.setLoanStatus(status);
                                            loanRequest.setCreatedAt(LocalDateTime.now());

                                            // Guardar la solicitud
                                            return loanRequestRepository.save(loanRequest);
                                        })
                        ).onErrorMap(e -> new RuntimeException("Error al registrar la solicitud: " + e.getMessage(), e));
    }



}