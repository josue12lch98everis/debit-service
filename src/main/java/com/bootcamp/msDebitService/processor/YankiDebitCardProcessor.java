package com.bootcamp.msDebitService.processor;

import com.bootcamp.msDebitService.models.dto.AddDebitCardEvent;
import com.bootcamp.msDebitService.models.dto.DebitAccountDTO;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Sinks;

@Component
public class YankiDebitCardProcessor {
    private final Sinks.Many<AddDebitCardEvent> sink;

    @Autowired
    public YankiDebitCardProcessor(Sinks.Many<AddDebitCardEvent> sink) {
        this.sink = sink;
    }

    public void process(DebitAccountDTO yanki, String pan) {
    	AddDebitCardEvent addDebitCardEvent = AddDebitCardEvent.builder().debitCardNumber(pan).
    			yankiAccountId("986").yankiBalance(BigDecimal.ONE).build();
    	
        System.out.println("paso: processor!!!!!!");
        
        sink.emitNext(addDebitCardEvent, Sinks.EmitFailureHandler.FAIL_FAST);
  
    }
}
