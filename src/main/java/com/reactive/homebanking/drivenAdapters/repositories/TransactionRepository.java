package com.reactive.homebanking.drivenAdapters.repositories;

import com.reactive.homebanking.models.Transaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TransactionRepository extends ReactiveMongoRepository<Transaction, String>{
}
