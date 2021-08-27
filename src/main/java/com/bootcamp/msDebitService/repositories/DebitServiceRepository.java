package com.bootcamp.msDebitService.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.bootcamp.msDebitService.models.entities.DebitCard;

import reactor.core.publisher.Mono;

public interface DebitServiceRepository extends ReactiveMongoRepository<DebitCard,String> {
    Mono<DebitCard> findByPan (String pan);
}
