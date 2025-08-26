package co.com.bancolombia.r2dbc.mapper;

import co.com.bancolombia.model.estado.Estado;
import co.com.bancolombia.r2dbc.entities.EstadoEntity;

public class EstadoMapper {

    public static EstadoEntity toEntity(Estado domain) {
        return EstadoEntity.builder()
                .id(domain.getId())
                .nombre(domain.getNombre())
                .build();
    }

    public static Estado toDomain(EstadoEntity entity) {
        return new Estado(entity.getId(), entity.getNombre(), entity.getDescripcion());
    }
}
