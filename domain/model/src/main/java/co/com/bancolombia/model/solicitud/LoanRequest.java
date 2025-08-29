package co.com.bancolombia.model.solicitud;
import co.com.bancolombia.model.estado.LoanStatus;
import co.com.bancolombia.model.tipoprestamo.LoanType;
import co.com.bancolombia.model.tipoprestamo.gateways.LoanTypeRepository;
import co.com.bancolombia.model.user.User;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
//import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoanRequest {

    private UUID id;
    private String email;
    private String documentNumber;
    private BigDecimal amount;
    private Integer termMonths;
    private LocalDateTime createdAt;
    private LoanType loanType;
    private LoanStatus loanStatus;
}
