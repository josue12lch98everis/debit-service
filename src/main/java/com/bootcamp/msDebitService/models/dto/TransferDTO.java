package com.bootcamp.msDebitService.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * The type Transfer.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferDTO {

    @Id
    @NotBlank
    private String id;

    @NotNull
    @NotBlank
    private String originAccount;

    @NotNull
    @NotBlank
    private String destinationAccount;

    @NotNull
    @NotBlank
    private double amount;

    @NotNull
    @NotBlank
    private String originTypeOfAccount;

    @NotNull
    @NotBlank
    private String destinationTypeOfAccount;
}
