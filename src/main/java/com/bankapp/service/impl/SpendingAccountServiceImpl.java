package com.bankapp.service.impl;



import com.bankapp.exception.ApplicationException;
import com.bankapp.repository.ClientRepository;
import com.bankapp.repository.SpendingAccountRepository;
import com.bankapp.model.SpendingAccount;
import com.bankapp.dao.ClientEntity;
import com.bankapp.dao.SpendingAccountEntity;
import com.bankapp.service.ISpendingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SpendingAccountServiceImpl implements ISpendingAccountService {

    @Autowired
    private SpendingAccountRepository spendingAccountRepository;

    @Autowired
    private ClientRepository clientRepository;



    @Override
    public List<SpendingAccount> findAll() throws ApplicationException {

        List<SpendingAccount> accounts = null;
        List<SpendingAccountEntity>  spendingAccountEntityList = (List<SpendingAccountEntity>) spendingAccountRepository.findAll();

        if(spendingAccountEntityList != null) {

            accounts = new ArrayList<>();

            for (int i = 0; i < spendingAccountEntityList.size(); i++) {

                SpendingAccount newSpendingAccount = parseSpendingAccountEntity(spendingAccountEntityList.get(i));
                accounts.add(newSpendingAccount);

            }
        }
        else {
            throw new ApplicationException("Spending accounts do not exist !");
        }

        return accounts;
    }

    @Override
    public SpendingAccount findById(int id) throws ApplicationException {

        SpendingAccount newSpendingAccount = null;
        SpendingAccountEntity se = spendingAccountRepository.findById(id).orElse(null);

        if(se != null) {

            newSpendingAccount = parseSpendingAccountEntity(se);
        }
        else {
            throw new ApplicationException("Spending account does not exist !");
        }

        return newSpendingAccount;
    }



    @Override
    public SpendingAccount save(SpendingAccount spendingAccountBody) throws ApplicationException {

        SpendingAccount resultSpending = null;

        if(spendingAccountBody.getAmount().compareTo(spendingAccountBody.getCredit()) != -1) {

            SpendingAccountEntity spEntity = null;

            ClientEntity clientEntity = clientRepository.findById(spendingAccountBody.getClientId()).orElse(null);

            if (clientEntity != null) {

                if(clientEntity.getSpendingAccountsById() == null) {
                    spEntity = parseSpendingAccount(spendingAccountBody, clientEntity);

                    SpendingAccountEntity resultSP = spendingAccountRepository.save(spEntity);

                    if(resultSP != null) {
                        resultSpending = parseSpendingAccountEntity(resultSP);
                    }
                    else {
                        throw  new ApplicationException("Spending Account not created !");
                    }
                }
            } else {
                throw  new ApplicationException("Client does not exist !");
            }
        }
        else {
            throw new ApplicationException("The balance is less than the minimum required");
        }

        return resultSpending;
    }

    @Override
    public boolean removeById(int id) throws ApplicationException {

        boolean deleted = false;

        SpendingAccountEntity spendingAccountEntity = spendingAccountRepository.findById(id).orElse(null);

        if(spendingAccountEntity != null) {

            ClientEntity clientEntity = clientRepository.findById(spendingAccountEntity.getClientByClientId().getId()).orElse(null);

            if (clientEntity != null) {

                if (clientEntity.getSavingAccountsById().size() == 0) {
                    spendingAccountRepository.deleteById(id);
                    deleted = true;
                }
              }
              else {
                  throw  new ApplicationException("Client does not exist !");
            }
        }
        else {
            throw new ApplicationException("Spending account does not exist !");
        }

        return deleted;
    }


    @Override
    public SpendingAccount updateSpendingAccount(int id, SpendingAccount spendingAccountBody) throws ApplicationException {

        SpendingAccount spendingAccountUpdated = null;

        SpendingAccountEntity se = spendingAccountRepository.findById(id).orElse(null);

        if (se != null) {

            if(spendingAccountBody.getAmount().compareTo(se.getSpendingCredit()) != -1) {

                se.setSpendingName(spendingAccountBody.getName());
                se.setSpendingAmount(spendingAccountBody.getAmount());
                se.setSpendingCredit(spendingAccountBody.getCredit().multiply(BigDecimal.valueOf(-1)));

                SpendingAccountEntity spendingAccountEntityUpdated = spendingAccountRepository.save(se);

                spendingAccountUpdated = parseSpendingAccountEntity(spendingAccountEntityUpdated);
            }
            else {
                throw new ApplicationException("The balance is less than the credit authorized");
            }
        } else {
            throw new ApplicationException("Spending account does not exist !");
        }
        return spendingAccountUpdated;
    }

    @Override
    public SpendingAccount findByClientId(int id) throws ApplicationException {

        SpendingAccountEntity se;
        SpendingAccount sa = new SpendingAccount();
        ClientEntity ce = clientRepository.findById(id).orElse(null);

        if(ce != null){

            se = spendingAccountRepository.findByClientByClientId(ce);

            if(se != null) {
                sa = parseSpendingAccountEntity(se);
            }
            else {
                throw  new ApplicationException("Spending account does not exist !");
            }
        }
        else {
            throw  new ApplicationException("Client does not exist !");
        }

        return sa;
    }

    @Override
    public boolean removeByClientId(int id) throws ApplicationException {

        boolean deleted = false;
        ClientEntity ce = clientRepository.findById(id).orElse(null);

        if(ce != null) {
            if(ce.getSavingAccountsById().size() == 0) {
                spendingAccountRepository.removeByClientByClientId(ce);
                deleted = true;
            }
        }
        else {
            throw new ApplicationException("Client does not exist !");
        }

        return deleted;
    }

    private SpendingAccountEntity parseSpendingAccount(SpendingAccount spendingAccount, ClientEntity clientEntity) {

        SpendingAccountEntity spendingAccountEntity = null;

        if(spendingAccount != null) {

            spendingAccountEntity = new SpendingAccountEntity();

            spendingAccountEntity.setSpendingId(spendingAccount.getId());
            spendingAccountEntity.setSpendingName(spendingAccount.getName());
            spendingAccountEntity.setSpendingAmount(spendingAccount.getAmount());
            spendingAccountEntity.setSpendingCredit(spendingAccount.getCredit().multiply(BigDecimal.valueOf(-1)));
            spendingAccountEntity.setClientByClientId(clientEntity);

        }

        return spendingAccountEntity;
    }

    private SpendingAccount parseSpendingAccountEntity(SpendingAccountEntity spendingAccountEntity) {

        SpendingAccount spendingAccount = null;

        if(spendingAccountEntity != null) {

            spendingAccount = new SpendingAccount();

            spendingAccount.setId(spendingAccountEntity.getSpendingId());
            spendingAccount.setName(spendingAccountEntity.getSpendingName());
            spendingAccount.setAmount(spendingAccountEntity.getSpendingAmount());
            spendingAccount.setCredit(spendingAccountEntity.getSpendingCredit().multiply(BigDecimal.valueOf(-1)));
            spendingAccount.setClientId(spendingAccountEntity.getClientByClientId().getId());
        }

        return spendingAccount;
    }
}
