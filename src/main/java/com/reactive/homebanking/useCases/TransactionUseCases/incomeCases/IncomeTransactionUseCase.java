package com.reactive.homebanking.useCases.TransactionUseCases.incomeCases;

import com.reactive.homebanking.drivenAdapters.repositories.TransactionRepository;
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
public class IncomeTransactionUseCase implements TransactionResource {

    private final double WITHOUT_CHARGE = 0;
    private final double ATM = 2;
    private double CHARGE;
    private final GetAccountByIdUseCase getAccountByIdUseCase;
    private final SaveAccountUseCase saveAccountUseCase;
    private final AccountMapper accountMapper;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Autowired
    public IncomeTransactionUseCase(GetAccountByIdUseCase getAccountByIdUseCase, SaveAccountUseCase saveAccountUseCase, AccountMapper accountMapper, TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.getAccountByIdUseCase = getAccountByIdUseCase;
        this.saveAccountUseCase = saveAccountUseCase;
        this.accountMapper = accountMapper;
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public Mono<TransactionResDto> apply(TransactionDto transactionDto) {

        if(transactionDto.getType().equals("STORE")){
            CHARGE = WITHOUT_CHARGE;
        }else{
            CHARGE = ATM;
        }

        Mono<AccountResDto> accountResDto = getAccountByIdUseCase.getAccount(transactionDto.getReceiverAccount());

        return accountResDto.flatMap(receiver -> {

            receiver.setTotalBalance(
                    receiver.getTotalBalance().add(
                            transactionDto.getAmount().subtract(BigDecimal.valueOf(CHARGE))
                    )
            );

            return saveAccountUseCase.saveAccount(receiver).flatMap(receiver1 -> {

                Transaction transaction = new Transaction.Builder(
                        accountMapper.resDtoToEntity(receiver1) , transactionDto.getAmount())
                        .setCharge(CHARGE)
                        .setTransactionType(transactionDto.getType())
                        .build();

                return transactionRepository.save(transaction).map(
                        transactionMapper::entityToResDto
                );
            });
        });
    }
}
