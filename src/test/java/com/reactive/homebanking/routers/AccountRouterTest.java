package com.reactive.homebanking.routers;

import com.reactive.homebanking.dtos.responseDtos.AccountResDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
public class AccountRouterTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void getAllAccountsRouterTest() {

        webTestClient.get().uri("/account/getAll")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(AccountResDto.class);
    }
}
