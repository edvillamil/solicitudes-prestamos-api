package co.com.bancolombia.model.tipoprestamo;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TipoPrestamo {

    private UUID id; // ID del tipo de préstamo
    private String nombre; // Nombre del tipo de préstamo
    private String descripcion;
}
