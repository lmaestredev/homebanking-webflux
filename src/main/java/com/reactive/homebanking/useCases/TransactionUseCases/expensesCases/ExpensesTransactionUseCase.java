package com.reactive.homebanking.useCases.TransactionUseCases.expensesCases;

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
public class ExpensesTransactionUseCase implements TransactionResource {

    private final double WITHOUT_CHARGE = 0;
    private final double WEB = 5;
    private final double POS = 1;
    private double CHARGE;
    private final GetAccountByIdUseCase getAccountByIdUseCase;
    private final SaveAccountUseCase saveAccountUseCase;
    private final AccountMapper accountMapper;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final RabbitMqPublisher rabbitMqPublisher;


    @Autowired
    public ExpensesTransactionUseCase(GetAccountByIdUseCase getAccountByIdUseCase, SaveAccountUseCase saveAccountUseCase, AccountMapper accountMapper, TransactionRepository transactionRepository, TransactionMapper transactionMapper, RabbitMqPublisher rabbitMqPublisher) {
        this.getAccountByIdUseCase = getAccountByIdUseCase;
        this.saveAccountUseCase = saveAccountUseCase;
        this.accountMapper = accountMapper;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
        this.rabbitMqPublisher = rabbitMqPublisher;
    }


    @Override
    public Mono<TransactionResDto> apply(TransactionDto transactionDto) {

        if(transactionDto.getType().equals("STORE")){
            CHARGE = WITHOUT_CHARGE;
        }else if(transactionDto.getType().equals("WEB")){
            CHARGE = WEB;
        }else{
            CHARGE = POS;
        }

        Mono<AccountResDto> accountResDto = getAccountByIdUseCase.getAccount(transactionDto.getReceiverAccount());

        return accountResDto.flatMap(receiver -> {

            receiver.setTotalBalance(
                    receiver.getTotalBalance().subtract(
                            transactionDto.getAmount().add(BigDecimal.valueOf(CHARGE))
                    )
            );

            return saveAccountUseCase.saveAccount(receiver).flatMap(receiver1 -> {

                Transaction transaction = new Transaction.Builder(
                        accountMapper.resDtoToEntity(receiver1) , transactionDto.getAmount())
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
    }
}
