package com.reactive.homebanking.useCases.ClientUseCases;

import com.reactive.homebanking.drivenAdapters.bus.RabbitMqPublisher;
import com.reactive.homebanking.drivenAdapters.repositories.ClientRepository;
import com.reactive.homebanking.dtos.responseDtos.ClientResDto;
import com.reactive.homebanking.utils.mappers.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class GetClienByIdUseCase {

    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;
    private final RabbitMqPublisher rabbitMqPublisher;


    @Autowired
    public GetClienByIdUseCase(ClientMapper clientMapper, ClientRepository clientRepository, RabbitMqPublisher rabbitMqPublisher) {
        this.clientMapper = clientMapper;
        this.clientRepository = clientRepository;
        this.rabbitMqPublisher = rabbitMqPublisher;
    }

    public Mono<ClientResDto> getClient(String id) {
        return clientRepository.findById(id).map(client -> {
            rabbitMqPublisher.publishClient(client);
            return clientMapper.entityToResDto(client);
        });
    }
}
