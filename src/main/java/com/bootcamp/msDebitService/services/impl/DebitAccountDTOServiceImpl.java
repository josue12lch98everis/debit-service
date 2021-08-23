package com.bootcamp.msDebitService.services.impl;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.bootcamp.msDebitService.models.dto.DebitAccountDTO;
import com.bootcamp.msDebitService.services.IDebitAccountDTOService;

import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Debit account dto service.
 */
@Service
public class DebitAccountDTOServiceImpl implements IDebitAccountDTOService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DebitAccountDTOServiceImpl.class);

    @Autowired
    @Qualifier("client")
    private WebClient.Builder client;


    @Override
    public Mono<DebitAccountDTO> updateDebit(String typeofdebit, DebitAccountDTO debitAccountDTO) {
        LOGGER.info("initializing Debit Update");
        LOGGER.info("El tipo de debito es: " + typeofdebit);
        LOGGER.info("El id del débito es: " + debitAccountDTO.getId());
        if(typeofdebit.equals("SAVING_ACCOUNT")) {
            return client.baseUrl("http://SAVINGACCOUNT-SERVICE/api/savingAccount")
                    .build()
                    .put()
                    .uri("/{id}", Collections.singletonMap("id", debitAccountDTO.getId()))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(debitAccountDTO)
                    .retrieve()
                    .bodyToMono(DebitAccountDTO.class);
        }else if(typeofdebit.equals("CURRENT_ACCOUNT")) {
            return client.baseUrl("http://CURRENTACCOUNT-SERVICE/api/currentAccount")
                    .build()
                    .put()
                    .uri("/{id}", Collections.singletonMap("id", debitAccountDTO.getId()))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(debitAccountDTO)
                    .retrieve()
                    .bodyToMono(DebitAccountDTO.class);
        }else if(typeofdebit.equals("FIXEDTERM_ACCOUNT")) {
            return client.baseUrl("http://localhost:8096")
                    .build()
                    .put()
                    .uri("/{id}", Collections.singletonMap("id", debitAccountDTO.getId()))
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(debitAccountDTO)
                    .retrieve()
                    .bodyToMono(DebitAccountDTO.class);
        }else return Mono.empty();
    }



    @Override
    public Mono<DebitAccountDTO> findByAccountNumber(String typeofdebit, String accountNumber) {
        Map<String, Object> params = new HashMap<String, Object>();
        LOGGER.info("initializing Debit query: " + typeofdebit);
        params.put("accountNumber", accountNumber);
        if (typeofdebit.equals("SAVING_ACCOUNT")) {
            return client.baseUrl("http://SAVINGACCOUNT-SERVICE/api/savingAccount")
                    .build()
                    .get()
                    .uri("/account/{accountNumber}", params)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchangeToMono(clientResponse -> clientResponse.bodyToMono(DebitAccountDTO.class))
                    .doOnNext(c -> LOGGER.info("Account Response: Account Amounth={}", c.getAmount()));
        }else if (typeofdebit.equals("CURRENT_ACCOUNT")) {
            return client.baseUrl("http://CURRENTACCOUNT-SERVICE/api/currentAccount")
                    .build()
                    .get()
                    .uri("/account/{accountNumber}", params)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchangeToMono(clientResponse -> clientResponse.bodyToMono(DebitAccountDTO.class))
                    .doOnNext(c -> LOGGER.info("CreditCard Response: CreditCard Amounth={}", c.getAmount()));
        } else if (typeofdebit.equals("FIXEDTERM_ACCOUNT")) {
            return client.baseUrl("http://localhost:8096")
                    .build()
                    .get()
                    .uri("/account/{accountNumber}", params)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchangeToMono(clientResponse -> clientResponse.bodyToMono(DebitAccountDTO.class))
                    .doOnNext(c -> LOGGER.info("CreditCard Response: CreditCard Amounth={}", c.getAmount()));
        } else {
            LOGGER.info("Entra aquí fallido");
            return Mono.empty();
        }
    }

   }
