package co.com.bancolombia.api.mapper;

import co.com.bancolombia.api.dto.LoanRequestDto;
import co.com.bancolombia.model.solicitud.LoanRequest;
import co.com.bancolombia.model.tipoprestamo.LoanType;

public class LoanMapper {

    public static LoanRequest toEntity(LoanRequestDto loanRequestDto) {

        if (loanRequestDto == null) return null;

        return LoanRequest.builder()
                .documentNumber(loanRequestDto.getDocument_number())
                .email(loanRequestDto.getEmail())
                .loanType(LoanType.builder().id(loanRequestDto.getLoan_type()).build())
                .termMonths(loanRequestDto.getTerm_months())
                .amount(loanRequestDto.getAmount())
                .build();
    }
}
