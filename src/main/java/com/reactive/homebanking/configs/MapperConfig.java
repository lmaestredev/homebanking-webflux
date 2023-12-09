package com.reactive.homebanking.configs;

import com.reactive.homebanking.utils.mappers.AccountMapper;
import com.reactive.homebanking.utils.mappers.ClientMapper;
import com.reactive.homebanking.utils.mappers.TransactionMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {
    @Bean
    public AccountMapper accountMapper(){
        return Mappers.getMapper(AccountMapper.class);
    }

    @Bean
    public ClientMapper clientMapper(){
        return Mappers.getMapper(ClientMapper.class);
    }

    @Bean
    public TransactionMapper transactionMapperMapper(){
        return Mappers.getMapper(TransactionMapper.class);
    }
}
