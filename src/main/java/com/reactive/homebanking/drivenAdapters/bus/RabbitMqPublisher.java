package com.reactive.homebanking.drivenAdapters.bus;

import com.reactive.homebanking.configs.RabbitConfig;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.rabbitmq.OutboundMessage;
import reactor.rabbitmq.Sender;

@Component
public class RabbitMqPublisher {

    @Value("${rabbitExchange}")
    public String EXCHANGE_NAME;

    @Autowired
    private Sender sender;

    @Autowired
    private Gson gson;

    public void publishClient(Object object){
        sender
                .send(Mono.just(new OutboundMessage(EXCHANGE_NAME,
                        RabbitConfig.CLIENT_ROUTING_KEY, gson.toJson(object).getBytes()))).subscribe();
    }

    public void publishAccount(Object object){
        sender
                .send(Mono.just(new OutboundMessage(EXCHANGE_NAME,
                        RabbitConfig.ACCOUNT_ROUTING_KEY, gson.toJson(object).getBytes()))).subscribe();
    }

    public void publishTransaction(Object object){
        sender
                .send(Mono.just(new OutboundMessage(EXCHANGE_NAME,
                        RabbitConfig.TRANSACTION_ROUTING_KEY, gson.toJson(object).getBytes()))).subscribe();
    }

    public void publishErrorMessage(Object object){
        sender
                .send(Mono.just(new OutboundMessage(EXCHANGE_NAME,
                        RabbitConfig.ERROR_ROUTING_KEY, gson.toJson(object).getBytes()))).subscribe();
    }
}
