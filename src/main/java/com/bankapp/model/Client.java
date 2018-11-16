package com.bankapp.model;

import java.util.List;

public class Client {

    private int id;
    private String lastname;
    private String firstname;
    private String email;
    private String phone;
    private List<Account> accounts;


    public Client() {
    }

    public Client(int id, String lastname, String firstname, String email, String phone, List<Account> accounts) {
        this.id = id;
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.phone = phone;
        this.accounts = accounts;
    }

    public Client(String lastname, String firstname, String email, String phone, List<Account> accounts) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.phone = phone;
        this.accounts = accounts;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
