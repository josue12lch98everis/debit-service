package com.bootcamp.msDebitService.services.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bootcamp.msDebitService.models.dto.AccountsDTO;
import com.bootcamp.msDebitService.models.dto.CustomerDTO;
import com.bootcamp.msDebitService.models.dto.DebitAccountDTO;
import com.bootcamp.msDebitService.models.dto.RetireDTO;
import com.bootcamp.msDebitService.models.entities.DebitCard;
import com.bootcamp.msDebitService.repositories.DebitServiceRepository;
import com.bootcamp.msDebitService.services.IDebitAccountDTOService;
import com.bootcamp.msDebitService.services.IDebitCardService;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class DebitServiceImpl implements IDebitCardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DebitServiceImpl.class);

    @Autowired
    @Qualifier("client")
    private WebClient.Builder client;

    @Autowired
    private DebitServiceRepository repository;
@Autowired 
private IDebitAccountDTOService debitAccountDTOService;
    @Override
    public Mono<DebitCard> create(DebitCard o) {
        return repository.save(o);
    }

    @Override
    public Flux<DebitCard> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<DebitCard> findById(String s) {
        return repository.findById(s);
    }

    @Override
    public Mono<DebitCard> update(DebitCard o) {
        return repository.save(o);
    }

    @Override
    public Mono<Void> delete(DebitCard o) {
        return repository.delete(o);
    }

    @Override
    public Mono<CustomerDTO> getCustomer(String customerIdentityNumber){
        Map<String, Object> params = new HashMap<String,Object>();
        LOGGER.info("initializing client query");
        params.put("customerIdentityNumber",customerIdentityNumber);
        return client
                .baseUrl("http://CUSTOMER-SERVICE/customer")
                .build()
                .get()
                .uri("/findCustomerCredit/{customerIdentityNumber}",customerIdentityNumber)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> clientResponse.bodyToMono(CustomerDTO.class))
                .doOnNext(c -> LOGGER.info("Customer Response: Customer={}", c.getName()));
    }

    @Override
    public Mono<DebitCard> findByPan(String pan) {
        return repository.findByPan(pan);
    }

    @Override
    public Mono<CustomerDTO> newPan(String id, CustomerDTO customerDTO) {
            LOGGER.info("initializing Customer cards");
                return client
                        .baseUrl("http://CUSTOMER-SERVICE/customer")
                        .build()
                        .put()
                        .uri("/cards/{id}", Collections.singletonMap("id", id))
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(customerDTO)
                        .retrieve()
                        .bodyToMono(CustomerDTO.class)
                        .doOnNext(c -> LOGGER.info("Customer Response: Customer={}", c.getName()));
    }
    
    @Override
    public Mono<RetireDTO> retire( RetireDTO retire) {
       
  return  client.baseUrl("http://retire-service/api/retire")
    .build()
    .post()
    .accept(MediaType.APPLICATION_JSON)
    .contentType(MediaType.APPLICATION_JSON)
    .bodyValue(retire)
    .retrieve()
    .bodyToMono(RetireDTO.class);
}

	@Override
	public Mono<DebitAccountDTO> getMainAccountFromDebitCard(String pan) {
		 Mono<AccountsDTO> accounts=findByPan(pan).flatMap(a ->{	
			 Optional<AccountsDTO> accountsDto=	 a.getAccounts().stream().filter(c->c.getOrder()==1).findFirst();
			 
				 return Mono.just(accountsDto.orElse(null) );
			  }) ;
				return	accounts.flatMap( mainAccount-> debitAccountDTOService.
						findByAccountNumber(  mainAccount.getTypeOfAccount(), mainAccount.getNumberOfAccount()));
				}
	
    }
