package co.com.bancolombia.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class LoanRequestPageable {

    private int page;
    private int size;
    private String email;
    private List<String> statuses;
}
