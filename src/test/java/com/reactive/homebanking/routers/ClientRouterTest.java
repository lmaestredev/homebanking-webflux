package com.reactive.homebanking.routers;

import com.reactive.homebanking.dtos.responseDtos.ClientResDto;
import com.reactive.homebanking.dtos.responseDtos.TransactionResDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class ClientRouterTest {

    @Autowired
    private WebTestClient client;

    @Test
    public void getAllClientsRouterTest() {
        client.get()
                .uri("/client/getAll")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ClientResDto.class);
    }
}
