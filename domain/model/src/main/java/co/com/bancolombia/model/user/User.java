package co.com.bancolombia.model.user;
import lombok.*;
//import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    UUID id;
    String name;
    String lastName;
    LocalDate birthDate;
    String address;
    String mobileNumber;
    String email;
    BigDecimal baseSalary;
}
