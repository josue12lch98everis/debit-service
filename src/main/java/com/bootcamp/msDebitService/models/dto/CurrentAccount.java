package com.bootcamp.msDebitService.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Account.
 */

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrentAccount {

    @Id
    private String id;

    private String typeOfAccount;

    @Indexed(unique=true)
    private String accountNumber;

    private double amount;

    private int maxLimitMovementPerMonth;

    private double commission;

    private int movementPerMonth;

    private String customerIdentityNumber;

    private double minAmountAveragePerMonth;

    private double amountAveragePerMonth;

    private CustomerDTO customer;

    @NotNull
    private List<Titular> titulares= new ArrayList<>();

    private List<Firmante> firmantes= new ArrayList<>();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOperation = LocalDateTime.now();
}
