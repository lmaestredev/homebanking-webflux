package com.reactive.homebanking.dtos.responseDtos;

import com.reactive.homebanking.models.Client;

import java.math.BigDecimal;

public class AccountResDto {

    private String id;
    private BigDecimal totalBalance;
    private Client cliente;

    public AccountResDto() {
    }

    public AccountResDto(String id, BigDecimal totalBalance, Client cliente) {
        this.id = id;
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
}
