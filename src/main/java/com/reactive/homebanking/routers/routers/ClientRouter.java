package com.reactive.homebanking.routers.routers;

import com.reactive.homebanking.dtos.requestDtos.ClientDto;
import com.reactive.homebanking.dtos.responseDtos.ClientResDto;
import com.reactive.homebanking.useCases.ClientUseCases.CreateClientUseCase;
import com.reactive.homebanking.useCases.ClientUseCases.GetAllClientUseCase;
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
public class ClientRouter {

    @Bean
    public RouterFunction<ServerResponse> createClientRouter(CreateClientUseCase createClientUseCase){
        Function<ClientDto, Mono<ServerResponse>> executor =
                resourceDTO -> createClientUseCase.createClient(resourceDTO)
                        .flatMap(result-> ServerResponse.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(result));

        return route(POST("/client/create")
                        .and(accept(MediaType.APPLICATION_JSON)),
                request -> request.bodyToMono(ClientDto.class).flatMap(executor));
    }

    @Bean
    public RouterFunction<ServerResponse> getAllClientsRouter(GetAllClientUseCase getAllClientUseCase){
        return route(GET("/client/getAll"),
                request -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromPublisher(getAllClientUseCase.getAll(), ClientResDto.class)));
    }
}
