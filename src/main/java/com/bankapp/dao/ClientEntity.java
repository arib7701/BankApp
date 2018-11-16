package com.bankapp.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "client", schema = "bankonet", catalog = "")
public class ClientEntity {
    private int id;
    private String lastname;
    private String firstname;
    private String email;
    private String phone;
    private Collection<SavingAccountEntity> savingAccountsById;
    private SpendingAccountEntity spendingAccountsById;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "lastname", nullable = false, length = 50)
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "firstname", nullable = false, length = 50)
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "email", nullable = false, length = 50)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "phone", nullable = false, length = 10)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientEntity that = (ClientEntity) o;
        return id == that.id &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastname, firstname, email, phone);
    }

    @OneToMany(mappedBy = "clientByClientId", cascade = CascadeType.ALL)
    @JsonIgnore
    public Collection<SavingAccountEntity> getSavingAccountsById() {
        return savingAccountsById;
    }

    public void setSavingAccountsById(Collection<SavingAccountEntity> savingAccountsById) {
        this.savingAccountsById = savingAccountsById;
    }

    @OneToOne(mappedBy = "clientByClientId", cascade = CascadeType.ALL)
    @JsonIgnore
    public SpendingAccountEntity getSpendingAccountsById() {
        return spendingAccountsById;
    }

    public void setSpendingAccountsById(SpendingAccountEntity spendingAccountsById) {
        this.spendingAccountsById = spendingAccountsById;
    }
}
