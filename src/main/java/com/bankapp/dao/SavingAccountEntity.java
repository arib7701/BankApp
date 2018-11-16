package com.bankapp.dao;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "saving_account", schema = "bankonet", catalog = "")
public class SavingAccountEntity {
    private int savingId;
    private String savingName;
    private BigDecimal savingAmount;
    private BigDecimal savingInterest;
    private BigDecimal savingMinimum;
    private ClientEntity clientByClientId;

    @Id
    @Column(name = "saving_id", nullable = false)
    public int getSavingId() {
        return savingId;
    }

    public void setSavingId(int savingId) {
        this.savingId = savingId;
    }

    @Basic
    @Column(name = "saving_name", nullable = false, length = 100)
    public String getSavingName() {
        return savingName;
    }

    public void setSavingName(String savingName) {
        this.savingName = savingName;
    }

    @Basic
    @Column(name = "saving_amount", nullable = false, precision = 2)
    public BigDecimal getSavingAmount() {
        return savingAmount;
    }

    public void setSavingAmount(BigDecimal savingAmount) {
        this.savingAmount = savingAmount;
    }

    @Basic
    @Column(name = "saving_interest", nullable = false, precision = 2)
    public BigDecimal getSavingInterest() {
        return savingInterest;
    }

    public void setSavingInterest(BigDecimal savingInterest) {
        this.savingInterest = savingInterest;
    }

    @Basic
    @Column(name = "saving_minimum", nullable = false, precision = 2)
    public BigDecimal getSavingMinimum() {
        return savingMinimum;
    }

    public void setSavingMinimum(BigDecimal savingMinimum) {
        this.savingMinimum = savingMinimum;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SavingAccountEntity that = (SavingAccountEntity) o;
        return savingId == that.savingId &&
                Objects.equals(savingName, that.savingName) &&
                Objects.equals(savingAmount, that.savingAmount) &&
                Objects.equals(savingInterest, that.savingInterest) &&
                Objects.equals(savingMinimum, that.savingMinimum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(savingId, savingName, savingAmount, savingInterest, savingMinimum);
    }

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    public ClientEntity getClientByClientId() {
        return clientByClientId;
    }

    public void setClientByClientId(ClientEntity clientByClientId) {
        this.clientByClientId = clientByClientId;
    }
}
