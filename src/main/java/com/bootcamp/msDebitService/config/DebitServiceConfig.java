package com.bootcamp.msDebitService.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bootcamp.msDebitService.consumer.EventConsumer;
import com.bootcamp.msDebitService.events.TransactionYankiEvent;
import com.bootcamp.msDebitService.events.YankiCreatedEvent;
import com.bootcamp.msDebitService.models.dto.AddDebitCardEvent;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;
import java.util.function.Supplier;

@Configuration
public class DebitServiceConfig {


  
    private final EventConsumer<AddDebitCardEvent> addDebitCardEventConsumer;
   
    @Autowired
    public DebitServiceConfig(EventConsumer<AddDebitCardEvent> addDebitCardEventConsumer) {
    
        this.addDebitCardEventConsumer=addDebitCardEventConsumer ;
    }


    @Bean
    public Sinks.Many<AddDebitCardEvent> sink() {
        return Sinks.many()
                .multicast()
                .directBestEffort();
    }

    @Bean
    public Supplier<Flux<AddDebitCardEvent>> transactionYankiEventPublisher( //orderPurchaseEventPublisher
            Sinks.Many<AddDebitCardEvent> publisher) {
        return publisher::asFlux;
    }
	@Bean 
	public Consumer<AddDebitCardEvent> asociationDebitCardProcessor(){
		return addDebitCardEventConsumer::consumeEvent;	
	}
 

 

}
