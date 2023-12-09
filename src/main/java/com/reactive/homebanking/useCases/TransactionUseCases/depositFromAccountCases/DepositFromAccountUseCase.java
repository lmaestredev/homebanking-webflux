package com.reactive.homebanking.useCases.TransactionUseCases.depositFromAccountCases;

import com.reactive.homebanking.drivenAdapters.bus.RabbitMqPublisher;
import com.reactive.homebanking.drivenAdapters.repositories.TransactionRepository;
import com.reactive.homebanking.dtos.publisherDto.TransactionPublisherDto;
import com.reactive.homebanking.dtos.requestDtos.TransactionDto;
import com.reactive.homebanking.dtos.responseDtos.AccountResDto;
import com.reactive.homebanking.dtos.responseDtos.TransactionResDto;
import com.reactive.homebanking.models.Transaction;
import com.reactive.homebanking.useCases.AccountUseCases.GetAccountByIdUseCase;
import com.reactive.homebanking.useCases.AccountUseCases.SaveAccountUseCase;
import com.reactive.homebanking.useCases.TransactionUseCases.TransactionResource;
import com.reactive.homebanking.utils.mappers.AccountMapper;
import com.reactive.homebanking.utils.mappers.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class DepositFromAccountUseCase implements TransactionResource {

    private final double CHARGE = 1.5;
    private final GetAccountByIdUseCase getAccountByIdUseCase;
    private final AccountMapper accountMapper;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final SaveAccountUseCase saveAccountUseCase;
    private final RabbitMqPublisher rabbitMqPublisher;

    @Autowired
    public DepositFromAccountUseCase(GetAccountByIdUseCase getAccountByIdUseCase, AccountMapper accountMapper, TransactionRepository transactionRepository, TransactionMapper transactionMapper, SaveAccountUseCase saveAccountUseCase, RabbitMqPublisher rabbitMqPublisher) {
        this.getAccountByIdUseCase = getAccountByIdUseCase;
        this.accountMapper = accountMapper;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.saveAccountUseCase = saveAccountUseCase;
        this.rabbitMqPublisher = rabbitMqPublisher;
    }

    @Override
    public Mono<TransactionResDto> apply(TransactionDto transactionDto) {

        Mono<AccountResDto> receiverMono = getAccountByIdUseCase.getAccount(transactionDto.getReceiverAccount());
        Mono<AccountResDto> senderMono = getAccountByIdUseCase.getAccount(transactionDto.getSenderAccount());


        return receiverMono.flatMap(receiver -> {

            receiver.setTotalBalance(receiver.getTotalBalance().add(transactionDto.getAmount().subtract(BigDecimal.valueOf(CHARGE))));

            return saveAccountUseCase.saveAccount(receiver).flatMap(receiver1 -> {

                return senderMono.flatMap(sender -> {

                    sender.setTotalBalance(sender.getTotalBalance().subtract(transactionDto.getAmount().add(BigDecimal.valueOf(CHARGE))));

                    return saveAccountUseCase.saveAccount(sender).flatMap(sender1 -> {

                        Transaction transaction = new Transaction.Builder(
                                accountMapper.resDtoToEntity(receiver1), transactionDto.getAmount())
                                .setSenderAccount(accountMapper.resDtoToEntity(sender1))
                                .setCharge(CHARGE)
                                .build();

                        return transactionRepository.save(transaction).map(transaction1 -> {

                            TransactionPublisherDto publisherDto = transactionMapper.entityToPublisher(transaction1);

                            if(transaction1.getSenderAccount() != null){
                                publisherDto.setSenderAccount(transaction1.getSenderAccount().toString());
                            }
                            publisherDto.setReceiverAccount(transaction1.getReceiverAccount().toString());
                            rabbitMqPublisher.publishTransaction(publisherDto);

                            return transactionMapper.entityToResDto(transaction1);
                        });
                    });
                });
            });
        });
    }
}


