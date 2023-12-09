package com.reactive.homebanking.useCases.AccountUseCases;

import com.reactive.homebanking.drivenAdapters.bus.RabbitMqPublisher;
import com.reactive.homebanking.drivenAdapters.repositories.AccountRepository;
import com.reactive.homebanking.dtos.publisherDto.AccountPublisherDto;
import com.reactive.homebanking.dtos.responseDtos.AccountResDto;
import com.reactive.homebanking.utils.mappers.AccountMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GetAccountByIdUseCase {

    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final RabbitMqPublisher rabbitMqPublisher;


    @Autowired
    public GetAccountByIdUseCase(AccountMapper accountMapper, AccountRepository accountRepository, RabbitMqPublisher rabbitMqPublisher) {
        this.accountMapper = accountMapper;
        this.accountRepository = accountRepository;
        this.rabbitMqPublisher = rabbitMqPublisher;
    }

    public Mono<AccountResDto> getAccount(String id) {
        return accountRepository.findById(id).map(account -> {

            AccountPublisherDto publisherDto = accountMapper.entityToPublisher(account);
            publisherDto.setCliente(account.getCliente().toString());
            rabbitMqPublisher.publishAccount(publisherDto);

            return accountMapper.entityToResDto(account);
        });
    }
}
