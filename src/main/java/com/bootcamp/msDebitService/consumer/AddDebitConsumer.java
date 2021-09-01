package com.bootcamp.msDebitService.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bootcamp.msDebitService.models.dto.AddDebitCardEvent;
import com.bootcamp.msDebitService.repositories.DebitServiceRepository;


import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
public class AddDebitConsumer implements EventConsumer<AddDebitCardEvent>{
	
		@Autowired
	    private DebitServiceRepository repository;
		 @Override
		    public void consumeEvent(AddDebitCardEvent event) {
			 log.info("===========Receiving request for associated DebitCard========= : {}", event);

		        
		      
		    }
	


}
