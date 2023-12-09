package com.reactive.homebanking.dtos.publisherDto;

import com.reactive.homebanking.models.Account;

import java.math.BigDecimal;

public class TransactionPublisherDto {

    private String id;
    private String senderAccount;
    private String receiverAccount;
    private BigDecimal amount;
    private double charge;
    private String transactionType;

    public TransactionPublisherDto() {
    }

    public TransactionPublisherDto(String id, String senderAccount, String receiverAccount, BigDecimal amount, double charge, String transactionType) {
        this.id = id;
        this.senderAccount = senderAccount;
        this.receiverAccount = receiverAccount;
        this.amount = amount;
        this.charge = charge;
        this.transactionType = transactionType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(String senderAccount) {
        this.senderAccount = senderAccount;
    }

    public String getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(String receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public double getCharge() {
        return charge;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }
}
