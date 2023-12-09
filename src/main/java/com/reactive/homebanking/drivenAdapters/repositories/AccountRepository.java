package com.reactive.homebanking.drivenAdapters.repositories;

import com.reactive.homebanking.models.Account;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface AccountRepository extends ReactiveMongoRepository<Account, String>{
}
