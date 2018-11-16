package com.bankapp.dao;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "spending_account", schema = "bankonet", catalog = "")
public class SpendingAccountEntity {
    private int spendingId;
    private String spendingName;
    private BigDecimal spendingAmount;
    private BigDecimal spendingCredit;
    private ClientEntity clientByClientId;

    @Id
    @Column(name = "spending_id", nullable = false)
    public int getSpendingId() {
        return spendingId;
    }

    public void setSpendingId(int spendingId) {
        this.spendingId = spendingId;
    }

    @Basic
    @Column(name = "spending_name", nullable = false, length = 100)
    public String getSpendingName() {
        return spendingName;
    }

    public void setSpendingName(String spendingName) {
        this.spendingName = spendingName;
    }

    @Basic
    @Column(name = "spending_amount", nullable = false, precision = 2)
    public BigDecimal getSpendingAmount() {
        return spendingAmount;
    }

    public void setSpendingAmount(BigDecimal spendingAmount) {
        this.spendingAmount = spendingAmount;
    }

    @Basic
    @Column(name = "spending_credit", nullable = false, precision = 2)
    public BigDecimal getSpendingCredit() {
        return spendingCredit;
    }

    public void setSpendingCredit(BigDecimal spendingCredit) {
        this.spendingCredit = spendingCredit;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpendingAccountEntity that = (SpendingAccountEntity) o;
        return spendingId == that.spendingId &&
                Objects.equals(spendingName, that.spendingName) &&
                Objects.equals(spendingAmount, that.spendingAmount) &&
                Objects.equals(spendingCredit, that.spendingCredit);

    }

    @Override
    public int hashCode() {
        return Objects.hash(spendingId, spendingName, spendingAmount, spendingCredit);
    }

    @OneToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    public ClientEntity getClientByClientId() {
        return clientByClientId;
    }

    public void setClientByClientId(ClientEntity clientByClientId) {
        this.clientByClientId = clientByClientId;
    }
}
