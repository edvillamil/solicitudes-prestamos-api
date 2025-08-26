package co.com.bancolombia.r2dbc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("solicitudes")
public class SolicitudEntity {

    @Id
    private UUID id;

    @Column("documento_cliente")
    private String documentoCliente;

    @Column("tipo_prestamo_id")
    private UUID tipoPrestamoId;

    @Column("monto")
    private BigDecimal monto;

    @Column("plazo_meses")
    private Integer plazoMeses;

    @Column("estado_id")
    private UUID estadoId;

    @Column("fecha_creacion")
    private LocalDateTime fechaCreacion;
}
