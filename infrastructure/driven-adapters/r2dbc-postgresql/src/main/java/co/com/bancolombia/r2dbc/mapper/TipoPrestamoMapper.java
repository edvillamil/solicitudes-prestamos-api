package co.com.bancolombia.r2dbc.mapper;

import co.com.bancolombia.model.tipoprestamo.LoanType;
import co.com.bancolombia.r2dbc.entities.LoanTypeEntity;

public class TipoPrestamoMapper {

    public static LoanTypeEntity toEntity(LoanType domain) {
        return LoanTypeEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .build();
    }

    public static LoanType toDomain(LoanTypeEntity entity) {
        return LoanType.builder()
                .id(entity.getId())
                .name(entity.getName())
                .amountMin(entity.getAmountMin())
                .amountMax(entity.getAmountMax())
                .termMin(entity.getTermMin())
                .termMax(entity.getTermMax())
                .rate(entity.getRate())
                .build();
    }
}
