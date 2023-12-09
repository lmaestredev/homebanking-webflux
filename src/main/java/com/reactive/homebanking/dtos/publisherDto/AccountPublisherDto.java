package com.reactive.homebanking.dtos.publisherDto;

import com.reactive.homebanking.models.Client;

import java.math.BigDecimal;

public class AccountPublisherDto {

    private String id;
    private BigDecimal totalBalance;
    private String cliente;

    public AccountPublisherDto() {
    }

    public AccountPublisherDto(String id, BigDecimal totalBalance, String cliente) {
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

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "AccountPublisherDto{" +
                "id='" + id + '\'' +
                ", totalBalance=" + totalBalance +
                ", cliente='" + cliente + '\'' +
                '}';
    }
}
