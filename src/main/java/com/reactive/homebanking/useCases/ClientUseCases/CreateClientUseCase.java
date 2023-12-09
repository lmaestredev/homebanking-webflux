package com.reactive.homebanking.useCases.ClientUseCases;


import com.reactive.homebanking.drivenAdapters.bus.RabbitMqPublisher;
import com.reactive.homebanking.drivenAdapters.repositories.ClientRepository;
import com.reactive.homebanking.dtos.requestDtos.ClientDto;
import com.reactive.homebanking.dtos.responseDtos.ClientResDto;
import com.reactive.homebanking.models.Client;
import com.reactive.homebanking.utils.mappers.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateClientUseCase {

    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;
    private final RabbitMqPublisher rabbitMqPublisher;


    @Autowired
    public CreateClientUseCase(ClientMapper clientMapper, ClientRepository clientRepository, RabbitMqPublisher rabbitMqPublisher) {
        this.clientMapper = clientMapper;
        this.clientRepository = clientRepository;
        this.rabbitMqPublisher = rabbitMqPublisher;
    }

    public Mono<ClientResDto> createClient(ClientDto clientDto) {

        Client client = clientMapper.dtoToEntity(clientDto);
        return clientRepository.save(client).map(client1 -> {
            rabbitMqPublisher.publishClient(client1);
            return clientMapper.entityToResDto(client1);
        });
    }
}
