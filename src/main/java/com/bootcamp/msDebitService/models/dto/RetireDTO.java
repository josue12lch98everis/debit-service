package com.bootcamp.msDebitService.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

/**
 * The type Retire.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@Builder
public class RetireDTO {
    @Id
    private String id;

    @Field(name = "amount")
    private Double amount;

    private String typeOfAccount;

    private String accountNumber;

    @Field(name = "retireDate")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime retireDate = LocalDateTime.now();
}
