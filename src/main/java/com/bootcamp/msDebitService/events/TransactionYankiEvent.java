package com.bootcamp.msDebitService.events;


import lombok.Data;

import java.util.ArrayList;
import java.util.Date;

@Data
public class TransactionYankiEvent implements Event{

    private static final String EVENT = "TransactionYanki";
    private double balance; //saldo
    private String imei;
    private String debitCardNumber; //numero tarjeta debito
    
    private Date date;

    @Override
    public String getEvent() {
        return EVENT;
    }
}
