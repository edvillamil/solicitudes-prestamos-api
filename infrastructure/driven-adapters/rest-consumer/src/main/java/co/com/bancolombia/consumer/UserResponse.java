package co.com.bancolombia.consumer;

import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class UserResponse {

    private UUID id;
    private String name;
    private String lastName;
    private LocalDate birthDate;
    private String address;
    private String mobileNumber;
    private String email;
    private BigDecimal baseSalary;
}