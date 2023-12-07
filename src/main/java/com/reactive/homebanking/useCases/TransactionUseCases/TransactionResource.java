package com.reactive.homebanking.useCases.TransactionUseCases;

import com.reactive.homebanking.dtos.requestDtos.TransactionDto;
import com.reactive.homebanking.dtos.responseDtos.TransactionResDto;
import reactor.core.publisher.Mono;

@FunctionalInterface
public interface TransactionResource {
    Mono<TransactionResDto> apply(TransactionDto transactionDto);
}
