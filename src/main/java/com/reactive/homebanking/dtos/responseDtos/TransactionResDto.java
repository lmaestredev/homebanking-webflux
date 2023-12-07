package com.reactive.homebanking.dtos.responseDtos;

import com.reactive.homebanking.models.Account;

import java.math.BigDecimal;

public class TransactionResDto {

    private String id;
    private Account senderAccount;
    private Account receiverAccount;
    private BigDecimal amount;
    private double charge;
    private String transactionType;

    public TransactionResDto() {

    }
    public TransactionResDto(String id, Account senderAccount, Account receiverAccount, BigDecimal amount, double charge, String transactionType) {
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

    public Account getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(Account senderAccount) {
        this.senderAccount = senderAccount;
    }

    public Account getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(Account receiverAccount) {
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