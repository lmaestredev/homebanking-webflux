package com.reactive.homebanking.useCases.TransactionUseCases;

import com.reactive.homebanking.drivenAdapters.bus.RabbitMqPublisher;
import com.reactive.homebanking.drivenAdapters.repositories.TransactionRepository;
import com.reactive.homebanking.dtos.publisherDto.AccountPublisherDto;
import com.reactive.homebanking.dtos.publisherDto.TransactionPublisherDto;
import com.reactive.homebanking.dtos.responseDtos.TransactionResDto;
import com.reactive.homebanking.utils.mappers.AccountMapper;
import com.reactive.homebanking.utils.mappers.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class GetAllTransactionsUseCase {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final RabbitMqPublisher rabbitMqPublisher;
    private final AccountMapper accountMapper;



    @Autowired
    public GetAllTransactionsUseCase(TransactionRepository transactionRepository, TransactionMapper transactionMapper, RabbitMqPublisher rabbitMqPublisher, AccountMapper accountMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.rabbitMqPublisher = rabbitMqPublisher;
        this.accountMapper = accountMapper;
    }


    public Flux<TransactionResDto> getAll() {
        return transactionRepository.findAll().map(transaction -> {

            TransactionPublisherDto publisherDto = transactionMapper.entityToPublisher(transaction);


            if(transaction.getSenderAccount() != null){
                publisherDto.setSenderAccount(transaction.getSenderAccount().toString());
            }
            publisherDto.setReceiverAccount(transaction.getReceiverAccount().toString());
            rabbitMqPublisher.publishTransaction(publisherDto);

            return transactionMapper.entityToResDto(transaction);
        });
    }
}
