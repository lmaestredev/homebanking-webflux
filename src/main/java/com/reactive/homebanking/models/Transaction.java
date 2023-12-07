package com.reactive.homebanking.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Document("Transactions")
public class Transaction{

    @Id
    private String id;
    private Account senderAccount;
    private Account receiverAccount;
    private BigDecimal amount;
    private double charge;
    private String transactionType;
    private Transaction(Builder builder) {
        this.senderAccount = builder.senderAccount;
        this.receiverAccount = builder.receiverAccount;
        this.amount = builder.amount;
        this.charge = builder.charge;
        this.transactionType = builder.transactionType;
    }

    public Transaction() {}

    public String getId() {
        return id;
    }

    public Account getSenderAccount() {
        return senderAccount;
    }

    public Account getReceiverAccount() {
        return receiverAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public double getCharge() {
        return charge;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setSenderAccount(Account senderAccount) {
        this.senderAccount = senderAccount;
    }

    public void setReceiverAccount(Account receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setCharge(double charge) {
        this.charge = charge;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public static class Builder{

        private final Account receiverAccount;
        private final BigDecimal amount;
        private Account senderAccount;
        private String transactionType;
        private double charge;

        public Builder(Account receiverAccount, BigDecimal amount){
            this.receiverAccount = receiverAccount;
            this.amount = amount;
        }

        public Builder setSenderAccount(Account senderAccount){
            this.senderAccount = senderAccount;
            return this;
        }


        public Builder setTransactionType(String transactionType){
            this.transactionType = transactionType;
            return this;
        }

        public Builder setCharge(double charge){
            this.charge = charge;
            return this;
        }

        public Transaction build() {
            return new Transaction(this);
        }
    }
}
