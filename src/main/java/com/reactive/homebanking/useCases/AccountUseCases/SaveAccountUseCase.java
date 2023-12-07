package com.reactive.homebanking.useCases.AccountUseCases;

import com.reactive.homebanking.drivenAdapters.repositories.AccountRepository;
import com.reactive.homebanking.dtos.requestDtos.AccountDto;
import com.reactive.homebanking.dtos.responseDtos.AccountResDto;
import com.reactive.homebanking.dtos.responseDtos.ClientResDto;
import com.reactive.homebanking.models.Account;
import com.reactive.homebanking.useCases.ClientUseCases.GetClienByIdUseCase;
import com.reactive.homebanking.utils.mappers.AccountMapper;
import com.reactive.homebanking.utils.mappers.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class SaveAccountUseCase {

    private final AccountMapper accountMapper;
    private final ClientMapper clientMapper;
    private final AccountRepository accountRepository;
    private final GetClienByIdUseCase getClienByIdUseCase;

    @Autowired
    public SaveAccountUseCase(AccountMapper accountMapper, ClientMapper clientMapper, AccountRepository accountRepository, GetClienByIdUseCase getClienByIdUseCase) {
        this.accountMapper = accountMapper;
        this.clientMapper = clientMapper;
        this.accountRepository = accountRepository;
        this.getClienByIdUseCase = getClienByIdUseCase;
    }

    public Mono<AccountResDto> saveAccount(AccountResDto account) {
        return accountRepository.save(accountMapper.resDtoToEntity(account)).map(
                    accountMapper::entityToResDto
        );
    }
}
