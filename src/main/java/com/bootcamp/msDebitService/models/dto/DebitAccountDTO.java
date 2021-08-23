package com.bootcamp.msDebitService.models.dto;

import lombok.*;

/**
 * The type Debit account dto.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DebitAccountDTO {

    private String id;
    private double amount;
    private String customerIdentityNumber;
    private String typeOfAccount;
    private String accountNumber;
}
