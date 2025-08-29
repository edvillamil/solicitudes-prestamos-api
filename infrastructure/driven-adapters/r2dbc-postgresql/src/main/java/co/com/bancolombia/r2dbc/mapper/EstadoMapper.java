package co.com.bancolombia.r2dbc.mapper;

import co.com.bancolombia.model.estado.LoanStatus;
import co.com.bancolombia.r2dbc.entities.LoanStatusEntity;

public class EstadoMapper {

    public static LoanStatusEntity toEntity(LoanStatus domain) {
        return LoanStatusEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .build();
    }

    public static LoanStatus toDomain(LoanStatusEntity entity) {
        return new LoanStatus(entity.getId(), entity.getName(), entity.getDescription());
    }
}
