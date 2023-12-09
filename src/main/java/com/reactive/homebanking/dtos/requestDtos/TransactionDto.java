package com.reactive.homebanking.dtos.requestDtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransactionDto
{
    private String senderAccount;
    private String receiverAccount;
    private BigDecimal amount;
    private String type;

    public TransactionDto(String senderAccount, String receiverAccount, BigDecimal amount, String type) {
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.amount = amount;
        this.type = type;
    }
}
