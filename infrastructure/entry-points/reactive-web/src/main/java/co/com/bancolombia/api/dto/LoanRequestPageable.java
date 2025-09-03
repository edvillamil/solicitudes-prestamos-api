package co.com.bancolombia.api.dto;

import lombok.Data;

@Data
public class LoanRequestPageable {

    private int page;
    private int size;
    private String email;
}
