package com.reactive.homebanking.configs;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.rabbitmq.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class RabbitConfig {

    //public static final String URI_NAME = "amqp://guest:guest@rabbitmq:5672";
    //public static final String URI_NAME = "amqp://guest:guest@localhost:5672";

    @Value("${rabbitUri}")
    public String URI_NAME;

    @Value("${rabbitExchange}")
    public String EXCHANGE_NAME;

    //routers key (una por cada cola)
    public static final String CLIENT_ROUTING_KEY = "clients.routing.key";
    public static final String ACCOUNT_ROUTING_KEY = "accounts.routing.key";
    public static final String TRANSACTION_ROUTING_KEY = "transactions.routing.key";
    public static final String ERROR_ROUTING_KEY = "errors.routing.key";

    //queues
    public static final String CLIENT_QUEUE = "clients-queue";
    public static final String ACCOUNT_QUEUE = "accounts-queue";
    public static final String TRANSACTION_QUEUE = "transactions-queue";
    public static final String ERROR_QUEUE = "error-queue";

    @Bean
    public AmqpAdmin amqpAdmin() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(URI.create(URI_NAME));
        var amqpAdmin =  new RabbitAdmin(connectionFactory);

        var exchange = new TopicExchange(EXCHANGE_NAME);
        var clientQueue = new Queue(CLIENT_QUEUE, true, false, false);
        var accountQueue = new Queue(ACCOUNT_QUEUE, true, false, false);
        var transactionQueue = new Queue(TRANSACTION_QUEUE, true, false, false);
        var errorQueue = new Queue(ERROR_QUEUE, true, false, false);

        amqpAdmin.declareExchange(exchange);
        amqpAdmin.declareQueue(clientQueue);
        amqpAdmin.declareQueue(accountQueue);
        amqpAdmin.declareQueue(transactionQueue);
        amqpAdmin.declareQueue(errorQueue);

        amqpAdmin.declareBinding(BindingBuilder.bind(clientQueue).to(exchange).with(CLIENT_ROUTING_KEY));
        amqpAdmin.declareBinding(BindingBuilder.bind(accountQueue).to(exchange).with(ACCOUNT_ROUTING_KEY));
        amqpAdmin.declareBinding(BindingBuilder.bind(transactionQueue).to(exchange).with(TRANSACTION_ROUTING_KEY));
        amqpAdmin.declareBinding(BindingBuilder.bind(errorQueue).to(exchange).with(ERROR_ROUTING_KEY));

        return amqpAdmin;
    }

    @Bean
    public ConnectionFactory connectionFactory() throws NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.useNio();
        connectionFactory.setUri(URI_NAME);
        return connectionFactory;
    }

    @Bean
    public Mono<Connection> connectionMono(@Value("spring.application.name") String name, ConnectionFactory connectionFactory)  {
        return Mono.fromCallable(() -> connectionFactory.newConnection(name)).cache();
    }

    @Bean
    public SenderOptions senderOptions(Mono<Connection> connectionMono) {
        return new SenderOptions()
                .connectionMono(connectionMono)
                .resourceManagementScheduler(Schedulers.boundedElastic());
    }

    @Bean
    public Sender sender(SenderOptions senderOptions) {
        return RabbitFlux.createSender(senderOptions);
    }


    @Bean
    public ReceiverOptions receiverOptions(Mono<Connection> connectionMono) {
        return new ReceiverOptions()
                .connectionMono(connectionMono);
    }

    @Bean
    public Receiver receiver(ReceiverOptions receiverOptions) {
        return RabbitFlux.createReceiver(receiverOptions);
    }
}
