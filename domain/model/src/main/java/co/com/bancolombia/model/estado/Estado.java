package co.com.bancolombia.model.estado;
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
public class Estado {
    private UUID id; // ID único de la solicitud
    private String nombre;
    private String descripcion;
}
