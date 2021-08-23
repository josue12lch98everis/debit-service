package com.bootcamp.msDebitService.services;

import com.bootcamp.msDebitService.models.dto.CustomerDTO;
import com.bootcamp.msDebitService.models.entities.DebitCard;

import reactor.core.publisher.Mono;

public interface IDebitCardService extends ICRUDService<DebitCard,String> {

    public Mono<CustomerDTO> getCustomer(String customerIdentityNumber);
    public Mono<DebitCard> findByPan(String pan);
    public Mono<CustomerDTO> newPan(String id, CustomerDTO customerDTO);
}
