package com.bankapp.service;

import com.bankapp.exception.ApplicationException;
import com.bankapp.model.SpendingAccount;

import java.util.List;

public interface ISpendingAccountService {

    List<SpendingAccount> findAll() throws ApplicationException;
    SpendingAccount findById(int id) throws ApplicationException;
    SpendingAccount save(SpendingAccount spendingAccount) throws ApplicationException;
    boolean removeById(int id) throws ApplicationException;
    SpendingAccount updateSpendingAccount(int id, SpendingAccount spendingAccount) throws ApplicationException;
    SpendingAccount findByClientId(int id) throws ApplicationException;
    boolean removeByClientId(int id) throws ApplicationException;
}
