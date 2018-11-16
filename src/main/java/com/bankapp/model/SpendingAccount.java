package com.bankapp.model;


import java.math.BigDecimal;

public class SpendingAccount extends Account {

    private BigDecimal credit;

    public SpendingAccount() {
    }

    public SpendingAccount(int id, String name, BigDecimal amount, int clientId) {
        super(id, name, amount, clientId);
    }

    public SpendingAccount(String name, BigDecimal amount, int clientId) {
        super(name, amount, clientId);
    }

    public SpendingAccount(BigDecimal credit) {
        this.credit = credit;
    }

    public SpendingAccount(int id, String name, BigDecimal amount, int clientId, BigDecimal credit) {
        super(id, name, amount, clientId);
        this.credit = credit;
    }

    public SpendingAccount(String name, BigDecimal amount, int clientId, BigDecimal credit) {
        super(name, amount, clientId);
        this.credit = credit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }
}
