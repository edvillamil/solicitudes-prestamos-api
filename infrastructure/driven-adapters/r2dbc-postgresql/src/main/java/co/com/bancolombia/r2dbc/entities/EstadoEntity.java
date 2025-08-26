package co.com.bancolombia.r2dbc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("estado_solicitud")
public class EstadoEntity {

    @Id
    private UUID id;

    private String nombre;

    private String descripcion;
}
