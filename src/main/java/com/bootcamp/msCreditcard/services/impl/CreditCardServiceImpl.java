package com.bootcamp.msCreditcard.services.impl;

import com.bootcamp.msCreditcard.models.dto.CustomerDTO;
import com.bootcamp.msCreditcard.models.entities.CreditCard;
import com.bootcamp.msCreditcard.repositories.CreditCardRepository;
import com.bootcamp.msCreditcard.services.ICreditCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class CreditCardServiceImpl implements ICreditCardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardServiceImpl.class);

    @Autowired
    @Qualifier("client")
    private WebClient.Builder client;

    @Autowired
    private CreditCardRepository repository;

    @Override
    public Mono<CreditCard> create(CreditCard o) {
        return repository.save(o);
    }

    @Override
    public Flux<CreditCard> findAll() {
        return repository.findAll();
    }

    @Override
    public Mono<CreditCard> findById(String s) {
        return repository.findById(s);
    }

    @Override
    public Mono<CreditCard> update(CreditCard o) {
        return repository.save(o);
    }

    @Override
    public Mono<Void> delete(CreditCard o) {
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
    public Mono<CreditCard> findByPan(String pan) {
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
}
