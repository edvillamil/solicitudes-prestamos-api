package co.com.bancolombia.r2dbc.mapper;

import co.com.bancolombia.model.solicitud.LoanRequest;
import co.com.bancolombia.r2dbc.entities.LoanStatusEntity;
import co.com.bancolombia.r2dbc.entities.LoanRequestEntity;
import co.com.bancolombia.r2dbc.entities.LoanTypeEntity;

public class SolicitudMapper {

    public static LoanRequestEntity toEntity(LoanRequest domain) {
        return LoanRequestEntity.builder()
                .id(domain.getId())
                .documentNumber(domain.getDocumentNumber())
                .email(domain.getEmail())
                .loanType(domain.getLoanType().getId())
                .amount(domain.getAmount())
                .termMonths(domain.getTermMonths())
                .statusId(domain.getLoanStatus().getId())
                .createdAt(domain.getCreatedAt())
                .build();
    }

    public static LoanRequest toDomain(LoanRequestEntity entity) {
        return LoanRequest.builder()
                .id(entity.getId())
                .documentNumber(entity.getDocumentNumber())
                .loanType(TipoPrestamoMapper
                        .toDomain(LoanTypeEntity.builder()
                                .id(entity.getLoanType())
                                .build()))
                .email(entity.getEmail())
                .amount(entity.getAmount())
                .termMonths(entity.getTermMonths())
                .loanStatus(EstadoMapper.toDomain(LoanStatusEntity.builder().id(entity.getStatusId()).build()))
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
