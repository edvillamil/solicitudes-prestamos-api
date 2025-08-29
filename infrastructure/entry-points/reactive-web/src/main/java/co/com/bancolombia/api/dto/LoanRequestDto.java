package co.com.bancolombia.api.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class LoanRequestDto {

    private String email;
    private String document_number;
    private BigDecimal amount;
    private Integer term_months;
    private UUID loan_type;
}
