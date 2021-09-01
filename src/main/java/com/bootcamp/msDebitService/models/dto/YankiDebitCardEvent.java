package com.bootcamp.msDebitService.models.dto;

import lombok.Builder;

import java.math.BigDecimal;

import com.bootcamp.msDebitService.events.Event;

@Builder
public class YankiDebitCardEvent implements Event{

    private static final String EVENT = "YankiAccountCreated";
    private BigDecimal yankiBalance;
    private String yankiAccountId;
    private String  debitCardNumber;

    @Override
    public String getEvent() {
        return EVENT;
    }
}
