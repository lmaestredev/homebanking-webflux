package com.reactive.homebanking.handlers.routers;

import com.reactive.homebanking.dtos.requestDtos.TransactionDto;
import com.reactive.homebanking.dtos.responseDtos.AccountResDto;
import com.reactive.homebanking.dtos.responseDtos.TransactionResDto;
import com.reactive.homebanking.useCases.TransactionUseCases.GetAllTransactionsUseCase;
import com.reactive.homebanking.useCases.TransactionUseCases.incomeCases.IncomeTransactionUseCase;
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
public class TransactionRouter {


    @Bean
    public RouterFunction<ServerResponse> createTransactionRouter(IncomeTransactionUseCase incomeTransactionRoyter){
        Function<TransactionDto, Mono<ServerResponse>> executor =
                resourceDTO -> incomeTransactionRoyter.apply(resourceDTO)
                        .flatMap(result-> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result));

        return route(POST("/transaction/income")
                        .and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(TransactionDto.class).flatMap(executor));
    }

    @Bean
    public RouterFunction<ServerResponse> getAllTransactionsRouter(GetAllTransactionsUseCase getAllTransactionsUseCase){
        return route(GET("/transaction/getAll"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllTransactionsUseCase.getAll(), TransactionResDto.class)));
    }
}
