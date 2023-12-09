package com.reactive.homebanking.useCases.AccountUseCases;

import com.reactive.homebanking.drivenAdapters.bus.RabbitMqPublisher;
import com.reactive.homebanking.drivenAdapters.repositories.AccountRepository;
import com.reactive.homebanking.dtos.publisherDto.AccountPublisherDto;
import com.reactive.homebanking.dtos.responseDtos.AccountResDto;
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
//    private final GetClienByIdUseCase getClienByIdUseCase;
    private final RabbitMqPublisher rabbitMqPublisher;

    @Autowired
    public SaveAccountUseCase(AccountMapper accountMapper, ClientMapper clientMapper, AccountRepository accountRepository, GetClienByIdUseCase getClienByIdUseCase, RabbitMqPublisher rabbitMqPublisher) {
        this.accountMapper = accountMapper;
        this.clientMapper = clientMapper;
        this.accountRepository = accountRepository;
//        this.getClienByIdUseCase = getClienByIdUseCase;
        this.rabbitMqPublisher = rabbitMqPublisher;
    }

    public Mono<AccountResDto> saveAccount(AccountResDto account) {
        return accountRepository.save(accountMapper.resDtoToEntity(account)).map(account1 -> {

                    AccountPublisherDto publisherDto = accountMapper.entityToPublisher(account1);
                    publisherDto.setCliente(account1.getCliente().toString());
                    rabbitMqPublisher.publishAccount(publisherDto);

                    return accountMapper.entityToResDto(account1);
                }
        );
    }
}
