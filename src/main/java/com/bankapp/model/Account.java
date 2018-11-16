package com.bankapp.model;

import java.math.BigDecimal;

public class Account {

    private int id;
    private String name;
    private BigDecimal amount;
    private int clientId;

    public Account() {
    }

    public Account(int id, String name, BigDecimal amount, int clientId) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.clientId = clientId;
    }

    public Account(String name, BigDecimal amount, int clientId) {
        this.name = name;
        this.amount = amount;
        this.clientId = clientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
