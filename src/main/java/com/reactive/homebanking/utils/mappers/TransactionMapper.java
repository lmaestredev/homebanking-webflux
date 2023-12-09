package com.reactive.homebanking.utils.mappers;

import com.reactive.homebanking.dtos.publisherDto.TransactionPublisherDto;
import com.reactive.homebanking.dtos.requestDtos.TransactionDto;
import com.reactive.homebanking.dtos.responseDtos.TransactionResDto;
import com.reactive.homebanking.models.Account;
import com.reactive.homebanking.models.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface TransactionMapper {
    TransactionResDto entityToResDto(Transaction transaction);

    @Mapping(target = "senderAccount", ignore = true)
    @Mapping(target = "receiverAccount", ignore = true)
    TransactionPublisherDto entityToPublisher(Transaction transaction);
}
