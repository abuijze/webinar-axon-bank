package org.axonframework.sample.axonbank.query;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TransactionHistory {

    @Id
    @GeneratedValue
    private Long id;

    private String accountId;
    private String transactionId;
    private Integer amount;

    public TransactionHistory() {
    }

    public TransactionHistory(String accountId, String transactionId, Integer amount) {
        this.accountId = accountId;
        this.transactionId = transactionId;
        this.amount = amount;
    }

    public TransactionHistory(String accountId) {
        this.accountId = accountId;
    }

    public Long getId() {
        return id;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
