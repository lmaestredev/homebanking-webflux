package com.reactive.homebanking.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document("Accounts")
public class Account
{
    @Id
    private String id;
    private BigDecimal totalBalance;
    private Client cliente;

    public Account(){}

    public Account(BigDecimal totalBalance, Client cliente) {
        this.totalBalance = totalBalance;
        this.cliente = cliente;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public Client getCliente() {
        return cliente;
    }

    public void setCliente(Client cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "M_CuentaMongo{" +
                "id='" + id + '\'' +
                ", totalBalance=" + totalBalance +
                ", cliente=" + cliente +
                '}';
    }
}
