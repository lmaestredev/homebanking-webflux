package com.reactive.homebanking.utils.mappers;

import com.reactive.homebanking.dtos.requestDtos.AccountDto;
import com.reactive.homebanking.dtos.responseDtos.AccountResDto;
import com.reactive.homebanking.models.Account;
import org.mapstruct.Mapper;

@Mapper
public interface AccountMapper {

    Account dtoToEntity(AccountDto accountDto);
    AccountResDto entityToResDto(Account account);
    Account resDtoToEntity(AccountResDto accountResDto);
}
