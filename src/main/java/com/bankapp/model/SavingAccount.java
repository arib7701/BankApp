package com.bankapp.model;

import java.math.BigDecimal;

public class SavingAccount extends Account {

    private BigDecimal interest;
    private BigDecimal minimum;

    public SavingAccount() {
    }

    public SavingAccount(int id, String name, BigDecimal amount, int clientId) {
        super(id, name, amount, clientId);
    }

    public SavingAccount(String name, BigDecimal amount, int clientId) {
        super(name, amount, clientId);
    }

    public SavingAccount(BigDecimal interest, BigDecimal minimum) {
        this.interest = interest;
        this.minimum = minimum;
    }

    public SavingAccount(int id, String name, BigDecimal amount, int clientId, BigDecimal interest, BigDecimal minimum) {
        super(id, name, amount, clientId);
        this.interest = interest;
        this.minimum = minimum;
    }

    public SavingAccount(String name, BigDecimal amount, int clientId, BigDecimal interest, BigDecimal minimum) {
        super(name, amount, clientId);
        this.interest = interest;
        this.minimum = minimum;
    }

    public BigDecimal getInterest() {
        return interest;
    }

    public void setInterest(BigDecimal interest) {
        this.interest = interest;
    }

    public BigDecimal getMinimum() {
        return minimum;
    }

    public void setMinimum(BigDecimal minimum) {
        this.minimum = minimum;
    }
}
