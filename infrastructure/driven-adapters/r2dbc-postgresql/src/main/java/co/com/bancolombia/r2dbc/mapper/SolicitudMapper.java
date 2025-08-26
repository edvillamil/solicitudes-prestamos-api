package co.com.bancolombia.r2dbc.mapper;

import co.com.bancolombia.model.solicitud.Solicitud;
import co.com.bancolombia.r2dbc.entities.EstadoEntity;
import co.com.bancolombia.r2dbc.entities.SolicitudEntity;
import co.com.bancolombia.r2dbc.entities.TipoPrestamoEntity;

public class SolicitudMapper {

    public static SolicitudEntity toEntity(Solicitud domain) {
        return SolicitudEntity.builder()
                .id(domain.getId())
                .documentoCliente(domain.getDocumentoCliente())
                .tipoPrestamoId(domain.getTipoPrestamo().getId())
                .monto(domain.getMonto())
                .plazoMeses(domain.getPlazoMeses())
                .estadoId(domain.getEstado().getId())
                .fechaCreacion(domain.getFechaCreacion())
                .build();
    }

    public static Solicitud toDomain(SolicitudEntity entity) {
        return Solicitud.builder()
                .documentoCliente(entity.getDocumentoCliente())
                .tipoPrestamo(TipoPrestamoMapper.toDomain(TipoPrestamoEntity.builder().id(entity.getTipoPrestamoId()).build()))
                .monto(entity.getMonto())
                .plazoMeses(entity.getPlazoMeses())
                .estado(EstadoMapper.toDomain(EstadoEntity.builder().id(entity.getEstadoId()).build()))
                .fechaCreacion(entity.getFechaCreacion())
                .build();
    }
}
