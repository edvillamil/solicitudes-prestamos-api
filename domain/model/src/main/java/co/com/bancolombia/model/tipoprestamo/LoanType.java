package co.com.bancolombia.model.tipoprestamo;
import lombok.*;
//import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoanType {

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
