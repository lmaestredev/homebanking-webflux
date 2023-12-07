package com.reactive.homebanking.useCases.ClientUseCases;


import com.reactive.homebanking.drivenAdapters.repositories.ClientRepository;
import com.reactive.homebanking.dtos.requestDtos.ClientDto;
import com.reactive.homebanking.dtos.responseDtos.ClientResDto;
import com.reactive.homebanking.models.Client;
import com.reactive.homebanking.utils.mappers.AccountMapper;
import com.reactive.homebanking.utils.mappers.ClientMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CreateClientUseCase {

    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;

    @Autowired
    public CreateClientUseCase(ClientMapper clientMapper, ClientRepository clientRepository) {
        this.clientMapper = clientMapper;
        this.clientRepository = clientRepository;
    }

    public Mono<ClientResDto> createClient(ClientDto clientDto) {

        Client client = clientMapper.dtoToEntity(clientDto);
        return clientRepository.save(client).map(
                clientMapper::entityToResDto
        );
    }
}
