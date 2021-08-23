package com.bootcamp.msDebitService.services;


import com.bootcamp.msDebitService.models.dto.CustomerDTO;

import reactor.core.publisher.Mono;

/**
 * The interface Customer dto service.
 */
public interface ICustomerDTOService {
    /**
     * Gets customer.
     *
     * @param customerIdentityNumber the customer identity number
     * @return the customer
     */
    public Mono<CustomerDTO> getCustomer(String customerIdentityNumber);
}
