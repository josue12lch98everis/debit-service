package com.bootcamp.msDebitService.models.dto;

import java.util.Date;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountsDTO {

   
   private String numberOfAccount;
   private String typeOfAccount;
   private int order;
    private Date createAt;
}
