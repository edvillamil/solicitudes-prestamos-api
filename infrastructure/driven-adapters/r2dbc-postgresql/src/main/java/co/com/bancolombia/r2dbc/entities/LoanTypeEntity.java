package co.com.bancolombia.r2dbc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("loan_type")
public class LoanTypeEntity {

    @Id
    private UUID id;
    private String name;
    private String description;
    private BigDecimal amountMin;
    private BigDecimal amountMax;
    private BigDecimal termMin;
    private BigDecimal termMax;
    private BigDecimal rate;
    private BigDecimal automaticValidation;
}
