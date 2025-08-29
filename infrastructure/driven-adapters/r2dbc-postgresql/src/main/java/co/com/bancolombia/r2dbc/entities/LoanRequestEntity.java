package co.com.bancolombia.r2dbc.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("loan_request")
public class LoanRequestEntity {

    @Id
    private UUID id;

    private String email;

    private String documentNumber;

    private UUID loanType;

    private BigDecimal amount;

    private Integer termMonths;

    private UUID statusId;

    private LocalDateTime createdAt;
}
