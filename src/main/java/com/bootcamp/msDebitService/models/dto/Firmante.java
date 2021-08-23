package com.bootcamp.msDebitService.models.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * The type Firmante.
 */
@Data
public class Firmante {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String dni;

    private String phone;

}
