package co.com.bancolombia.model.solicitud;
import co.com.bancolombia.model.estado.Estado;
import co.com.bancolombia.model.tipoprestamo.TipoPrestamo;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Solicitud {

    private UUID id; // ID único de la solicitud
    private String documentoCliente; // Documento de identidad del cliente
    private TipoPrestamo tipoPrestamo; // Tipo de préstamo
    private BigDecimal monto; // Monto solicitado
    private Integer plazoMeses; // Plazo en meses
    private Estado estado; // Estado de la solicitud
    private LocalDateTime fechaCreacion; // Fecha de registro

}
