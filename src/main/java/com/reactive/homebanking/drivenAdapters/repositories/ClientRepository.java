package com.reactive.homebanking.drivenAdapters.repositories;

import com.reactive.homebanking.models.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ClientRepository extends ReactiveMongoRepository<Client, String> {
}
