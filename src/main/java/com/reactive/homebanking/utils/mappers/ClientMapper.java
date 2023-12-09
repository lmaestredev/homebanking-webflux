package com.reactive.homebanking.utils.mappers;

import com.reactive.homebanking.dtos.requestDtos.ClientDto;
import com.reactive.homebanking.dtos.responseDtos.ClientResDto;
import com.reactive.homebanking.models.Client;
import org.mapstruct.Mapper;

@Mapper
public interface ClientMapper {

    Client dtoToEntity(ClientDto clientDto);
    ClientResDto entityToResDto(Client  client);

    Client resDtoToEntity(ClientResDto clientResDto);

}
