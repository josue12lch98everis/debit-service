package com.bootcamp.msDebitService.models.implementations;

import java.util.function.Function;

import com.bootcamp.msDebitService.models.entities.DebitCard;

public interface AccountValidation extends Function<DebitCard, Boolean> {
	

}
