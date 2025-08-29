package co.com.bancolombia.model.estado;
import lombok.*;
//import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoanStatus {
    private UUID id;
    private String name;
    private String description;
}
