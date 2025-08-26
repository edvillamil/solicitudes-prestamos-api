package co.com.bancolombia.usecase.registrarsolicitudprestamo;

import co.com.bancolombia.model.estado.gateways.EstadoRepository;
import co.com.bancolombia.model.solicitud.Solicitud;
import co.com.bancolombia.model.solicitud.gateways.SolicitudRepository;
import co.com.bancolombia.model.tipoprestamo.gateways.TipoPrestamoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Log4j2
@RequiredArgsConstructor
public class RegistrarSolicitudPrestamoUseCase {

    private final SolicitudRepository solicitudRepository;
    private final TipoPrestamoRepository tipoPrestamoRepository;
    private final EstadoRepository estadoRepository;

    public Mono<Solicitud> registrar(Solicitud solicitud) {
        log.info("Registrando solicitud para el cliente: {}", solicitud.getDocumentoCliente());

        return tipoPrestamoRepository.findById(solicitud.getTipoPrestamo().getId())
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Tipo de préstamo no existe")))
                .flatMap(tipo -> estadoRepository.findByNombre("Pendiente de revisión")
                        .switchIfEmpty(Mono.error(new IllegalStateException("Estado inicial no encontrado")))
                        .flatMap(estado -> {
                            solicitud.setTipoPrestamo(tipo);
                            solicitud.setEstado(estado);
                            solicitud.setFechaCreacion(LocalDateTime.now());
                            return solicitudRepository.save(solicitud);
                        })
                )
                .doOnError(e -> log.error("Error registrando solicitud: {}", e.getMessage(), e));
    }
}
