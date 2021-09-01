package com.bootcamp.msDebitService.controller;



import java.awt.Adjustable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.bootcamp.msDebitService.models.dto.AccountsDTO;
import com.bootcamp.msDebitService.models.dto.DebitAccountDTO;
import com.bootcamp.msDebitService.models.dto.RetireDTO;
import com.bootcamp.msDebitService.models.entities.DebitCard;
import com.bootcamp.msDebitService.processor.YankiDebitCardProcessor;
import com.bootcamp.msDebitService.services.IDebitAccountDTOService;
import com.bootcamp.msDebitService.services.IDebitCardService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping()
public class DebitServiceController {


	 private static final Logger LOGGER = LoggerFactory.getLogger(DebitServiceController.class);
	@Autowired 
	YankiDebitCardProcessor yankiDebitCardProcessor;
	 @Autowired
		private IDebitCardService debitCardService;
		@Autowired
		private IDebitAccountDTOService debitAccountDTOService;
	
		@GetMapping()
		public Flux<DebitCard> getAllDebitCard() {
			
			Flux<DebitCard> debitCards = debitCardService.findAll().map(debitCard -> {
				return debitCard;
			});
			return debitCards;
		}
		
		@PostMapping()
		public Mono<DebitCard> saveDebitCard(@RequestBody DebitCard debitCard) {
			debitCard.getAccounts().forEach(a-> a.setCreateAt(new Date()));
			
			Mono<DebitCard> debitCardCreate = debitCardService.create(debitCard);
			return debitCardCreate;
		
		}
		
		@PostMapping("/payProduct")
		public Mono<DebitCard> payProductDebitCard(@RequestBody DebitCard debitCard, double amountOfDebit) {
			
			List<Mono<DebitAccountDTO>> accounts = new ArrayList<>();
			
			debitCard.getAccounts().forEach(acc->{
				accounts.add(acc.getOrder()-1 ,debitAccountDTOService.
					findByAccountNumber(acc.getTypeOfAccount(),acc.getNumberOfAccount()).flatMap((a)-> {
						
						return Mono.just(a) ;
					}));
			});
			Flux<DebitAccountDTO> debitAccounts =Flux.concat(accounts);
			Mono<Double>  amountInAccount=debitAccounts.flatMap((d)->Mono.just(d.getAmount())).reduce(0.0, Double::sum) ;
			LOGGER.info("amountInAccount");
			if (amountInAccount.block() < amountOfDebit) {
				LOGGER.info("amountInAccount");
			}else {
				
			}
			return null;
			
		}
		
		
	
			 
			 
	


		@DeleteMapping("/{pan}")
		public Mono<Void> deleteByID(@PathVariable(name="pan",required= true) String pan) {
		
		return	debitCardService.findByPan(pan).flatMap(a->debitCardService.delete(a) );
			
		}
		@GetMapping("findByPan/{pan}")
		public Mono<DebitCard> findByID(@PathVariable(name="pan",required= true) String pan) {
		
		return	debitCardService.findByPan(pan);
			
			}
		
		@GetMapping("/getBalance/{pan}")
		public Mono<DebitAccountDTO> getBalance(@PathVariable(name="pan",required= true) String pan ) {
			
			 Mono<AccountsDTO> accounts=debitCardService.findByPan(pan).flatMap(a ->{	
			 Optional<AccountsDTO> accountsDto=	 a.getAccounts().stream().filter(c->c.getOrder()==1).findFirst();
			 
				 return Mono.just(accountsDto.orElse(null) );
			  }) ;
				return	accounts.flatMap( mainAccount-> debitAccountDTOService.
						findByAccountNumber(  mainAccount.getTypeOfAccount(), mainAccount.getNumberOfAccount()));
				}
		@GetMapping("/deposit/{pan}/{amount}")
		public Mono<RetireDTO> retire(@PathVariable(name="pan",required= true) String pan, 
				@PathVariable(name="amount",required = true) double amount) {
			
			Comparator<AccountsDTO> comparador = Comparator.comparing(AccountsDTO::getOrder);
			 Flux<AccountsDTO> accounts=debitCardService.findByPan(pan).flatMapMany(a ->	
			  Flux.fromStream( a.getAccounts().stream().sorted(comparador)) ) ;
			 
		
			 
		return	 accounts.flatMap( mainAccount-> debitAccountDTOService.
				findByAccountNumber(  mainAccount.getTypeOfAccount(), mainAccount.getNumberOfAccount())).
 filter(account->account.getAmount()>amount).limitRequest(1).
 flatMap(debitAccount->  debitCardService.retire
		 ( RetireDTO.builder()
				 .accountNumber(debitAccount.getAccountNumber())
				 .amount(amount)
				 .typeOfAccount(debitAccount.getTypeOfAccount())
				 .build()  )).next().defaultIfEmpty(null) ;
		}
		
		
		@GetMapping("/getMainAccount/{pan}")
		public Mono<DebitAccountDTO> getMainAccount(@PathVariable(name="pan",required= true) String pan ) {
			
			return debitCardService.getMainAccountFromDebitCard(pan).doOnSuccess(acc-> {
				yankiDebitCardProcessor.process(acc, pan);
				
				
			});
		}
		
}
	


