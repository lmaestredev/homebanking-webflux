package com.reactive.homebanking.handlers.bus;


import com.reactive.homebanking.RabbitConfig;
import com.reactive.homebanking.dtos.requestDtos.AccountDto;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.rabbitmq.Receiver;

@Component
public class RabbitMqMessageConsumer implements CommandLineRunner {

    @Autowired
    private Receiver receiver;

    @Autowired
    private Gson gson;


    @Override
    public void run(String... args) throws Exception {
        receiver.consumeAutoAck(RabbitConfig.QUEUE_NAME)
                .map(message -> {
                   AccountDto transaction = gson
                           .fromJson(new String(message.getBody()),
                                   AccountDto.class);

                    System.out.println("La cuenta creada fue:  " + transaction);
                    return transaction;
                }).subscribe();
    }
}
