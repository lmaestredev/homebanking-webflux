package com.reactive.homebanking.useCases.AccountUseCases;

import com.reactive.homebanking.drivenAdapters.repositories.AccountRepository;
import com.reactive.homebanking.dtos.responseDtos.AccountResDto;
import com.reactive.homebanking.utils.mappers.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class GetAllAccountUseCase {
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;

    @Autowired
    public GetAllAccountUseCase(AccountMapper accountMapper, AccountRepository accountRepository) {
        this.accountMapper = accountMapper;
        this.accountRepository = accountRepository;
    }

    public Flux<AccountResDto> getAll() {
        return accountRepository.findAll().map(
                accountMapper::entityToResDto
        );
    }
}
