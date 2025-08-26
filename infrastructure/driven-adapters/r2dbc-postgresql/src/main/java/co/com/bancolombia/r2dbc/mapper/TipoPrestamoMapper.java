package co.com.bancolombia.r2dbc.mapper;

import co.com.bancolombia.model.tipoprestamo.TipoPrestamo;
import co.com.bancolombia.r2dbc.entities.TipoPrestamoEntity;

public class TipoPrestamoMapper {

    public static TipoPrestamoEntity toEntity(TipoPrestamo domain) {
        return TipoPrestamoEntity.builder()
                .id(domain.getId())
                .nombre(domain.getNombre())
                .descripcion(domain.getDescripcion())
                .build();
    }

    public static TipoPrestamo toDomain(TipoPrestamoEntity entity) {
        return new TipoPrestamo(entity.getId(), entity.getNombre(), entity.getDescripcion());
    }
}
