package com.reactive.homebanking.routers.routers;

import com.reactive.homebanking.dtos.requestDtos.AccountDto;
import com.reactive.homebanking.dtos.responseDtos.AccountResDto;
import com.reactive.homebanking.useCases.AccountUseCases.CreateAccountUseCase;
import com.reactive.homebanking.useCases.AccountUseCases.GetAllAccountUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class AccountRouter {

    @Bean
    public RouterFunction<ServerResponse> createAccountRouter(CreateAccountUseCase createAccountUseCase){
        Function<AccountDto, Mono<ServerResponse>> executor =
                resourceDTO -> createAccountUseCase.createAccount(resourceDTO)
                        .flatMap(result-> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result));

        return route(POST("/account/create")
                        .and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(AccountDto.class).flatMap(executor));
    }

    @Bean
    public RouterFunction<ServerResponse> getAllAccountsRouter(GetAllAccountUseCase getAllAccountUseCase){
        return route(GET("/account/getAll"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllAccountUseCase.getAll(), AccountResDto.class)));
    }

//    @Bean
//    public RouterFunction<ServerResponse> getAccountByIdRouter(GetAccountByIdUseCase getAccountByIdRouter){
//        final String uri = "/account/getById/{idAccount}";
//
//        return route(GET(uri), getAccountByIdRouter::getAccount);
//    }
}
