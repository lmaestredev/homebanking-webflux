package com.reactive.homebanking.useCases.TransactionUseCases;

import com.reactive.homebanking.drivenAdapters.repositories.TransactionRepository;
import com.reactive.homebanking.dtos.responseDtos.TransactionResDto;
import com.reactive.homebanking.utils.mappers.TransactionMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class GetAllTransactionsUseCase {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public GetAllTransactionsUseCase(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }


    public Flux<TransactionResDto> getAll() {
        return transactionRepository.findAll().map(
                transactionMapper::entityToResDto
        );
    }
}
