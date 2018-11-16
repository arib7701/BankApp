package com.bankapp.service.impl;


import com.bankapp.exception.ApplicationException;
import com.bankapp.repository.ClientRepository;
import com.bankapp.repository.SavingAccountRepository;
import com.bankapp.repository.SpendingAccountRepository;
import com.bankapp.dao.ClientEntity;
import com.bankapp.dao.SavingAccountEntity;
import com.bankapp.dao.SpendingAccountEntity;
import com.bankapp.model.SavingAccount;
import com.bankapp.service.ISavingAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SavingAccountServiceImpl implements ISavingAccountService {

    @Autowired
    SavingAccountRepository savingAccountRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    SpendingAccountRepository spendingAccountRepository;


    @Override
    public List<SavingAccount> findAll()  throws ApplicationException {

        List<SavingAccount> allSavingAccounts = new ArrayList<>();
        List<SavingAccountEntity> allSavingEntity = (List<SavingAccountEntity>) savingAccountRepository.findAll();

        if(allSavingEntity != null) {

            for (SavingAccountEntity se : allSavingEntity) {

                SavingAccount saving = parseSavingAccountEntity(se);

                allSavingAccounts.add(saving);
            }
        }
        else {
            throw new ApplicationException("Saving Accounts do not exist");
        }

        return allSavingAccounts;
    }

    @Override
    public List<SavingAccount> findAllByClientId(int id) throws ApplicationException {

        ClientEntity clientEntity = clientRepository.findById(id).orElse(null);
        List<SavingAccount> savingClients = new ArrayList<>();

        if(clientEntity != null){

            List<SavingAccountEntity> savingAccountEntitiesClient = savingAccountRepository.findByClientByClientId(clientEntity);

            if(savingAccountEntitiesClient != null) {
                for (SavingAccountEntity se : savingAccountEntitiesClient) {

                    SavingAccount saving = parseSavingAccountEntity(se);

                    savingClients.add(saving);
                }
            }
            else {
                throw new ApplicationException("Saving Accounts do not exist");
            }
        }
        else {
            throw new ApplicationException("Client does not exist");
        }

        return savingClients;
    }

    @Override
    public SavingAccount findById(int id)  throws ApplicationException {

        SavingAccountEntity se = savingAccountRepository.findById(id).orElse(null);
        SavingAccount saving = null;

        if(se != null){
            saving = parseSavingAccountEntity(se);
        }
        else {
            throw new ApplicationException("Saving Account does not exist");
        }

        return saving;
    }

    @Override
    public SavingAccount save(SavingAccount savingAccount) throws ApplicationException {

        SavingAccount resultSaving = null;

        if(savingAccount.getAmount().compareTo(savingAccount.getMinimum()) != -1) {

            int clientId = savingAccount.getClientId();
            ClientEntity clientEntity = clientRepository.findById(clientId).orElse(null);

            SavingAccountEntity savingAccountEntity = parseSavingAccount(savingAccount, clientEntity);

            if (clientEntity != null) {

                savingAccountEntity.setClientByClientId(clientEntity);
                SavingAccountEntity resultSE = savingAccountRepository.save(savingAccountEntity);

                resultSaving = parseSavingAccountEntity(resultSE);
            }
            else {
                throw new ApplicationException("Client does not exist, can not save the account");
            }
        }
        else {
            throw new ApplicationException("Error: Saving Account balance is less than minimum required");
        }

        return resultSaving;
    }

    @Override
    public boolean removeById(int id)  throws ApplicationException {

        SavingAccountEntity savingAccountEntity = savingAccountRepository.findById(id).orElse(null);
        boolean deleted = false;

        if(savingAccountEntity != null) {

            if (savingAccountEntity.getSavingAmount().compareTo(BigDecimal.valueOf(0).movePointLeft(2)) == 1) {

                ClientEntity clientEntity = savingAccountEntity.getClientByClientId();

                if (clientEntity != null) {

                    SpendingAccountEntity spendingAccountEntity = spendingAccountRepository.findByClientByClientId(clientEntity);

                    if (spendingAccountEntity != null) {

                        spendingAccountEntity.setSpendingAmount(spendingAccountEntity.getSpendingAmount().add(savingAccountEntity.getSavingAmount()));
                        spendingAccountRepository.save(spendingAccountEntity);
                        savingAccountRepository.deleteById(id);
                        deleted = true;
                    }
                    else {
                        throw new ApplicationException("Spending Account does not exist, can not transfer the balance and close the account.");
                    }
                }
                else {
                    throw new ApplicationException("Client does not exist, can not close the account");
                }
            }
            else if (savingAccountEntity.getSavingAmount().compareTo(BigDecimal.valueOf(0).movePointLeft(2)) == 0) {
                savingAccountRepository.deleteById(id);
                deleted = true;
            }
        }
        else {
            throw new ApplicationException("Saving Account does not exist");
        }

        return deleted;
    }

    @Override
    @Transactional
    public boolean removeByClientId(int id)  throws ApplicationException {

        ClientEntity clientEntity = clientRepository.findById(id).orElse(null);

        boolean deleted = false;

        if(clientEntity != null){

            SpendingAccountEntity spendingAccountEntity = spendingAccountRepository.findByClientByClientId(clientEntity);

            int counter = 0;

            List<SavingAccountEntity> savingAccountEntities = savingAccountRepository.findByClientByClientId(clientEntity);

            if(savingAccountEntities != null){

                for(SavingAccountEntity se : savingAccountEntities){

                    if (se.getSavingAmount().compareTo(BigDecimal.valueOf(0).movePointLeft(2)) == 1) {

                        if (spendingAccountEntity != null) {

                            spendingAccountEntity.setSpendingAmount(spendingAccountEntity.getSpendingAmount().add(se.getSavingAmount()));
                            spendingAccountRepository.save(spendingAccountEntity);
                            counter++;
                        }
                        else {
                            throw new ApplicationException("Spending Account does not exist, can not transfer the balance and close the account.");
                        }
                    }
                    else if (se.getSavingAmount().compareTo(BigDecimal.valueOf(0).movePointLeft(2)) == 0) {
                        counter++;
                    }
                    else {
                        throw new ApplicationException("At least one Saving Account has a negative balance and can't be closed");
                    }
                }

                if(counter == savingAccountEntities.size()){
                    savingAccountRepository.deleteByClientByClientId(clientEntity);
                    deleted = true;
                }
            }
            else {
                throw new ApplicationException("Saving Accounts do not exist");
            }
        }
        else {
            throw new ApplicationException("Client does not exist");
        }

        return deleted;
    }

    @Override
    public SavingAccount updateSavingAccount(int id, SavingAccount savingAccount) throws ApplicationException {

        SavingAccount savingAccountUpdated = null;

        if(savingAccount.getAmount().compareTo(savingAccount.getMinimum()) != -1){

            SavingAccountEntity savingAccountEntity = savingAccountRepository.findById(id).orElse(null);

            if(savingAccountEntity != null){

                savingAccountEntity = parseSavingAccount(savingAccount, savingAccountEntity.getClientByClientId());

                SavingAccountEntity savingAccountEntityUpdated = savingAccountRepository.save(savingAccountEntity);

                savingAccountUpdated = parseSavingAccountEntity(savingAccountEntityUpdated);
            }
            else {
                throw new ApplicationException("Saving Account does not exist");
            }
        }
        else {
            throw new ApplicationException("Error: Saving Account balance is less than minimum required");
        }

        return savingAccountUpdated;
    }


    private SavingAccountEntity parseSavingAccount(SavingAccount savingAccount, ClientEntity clientEntity) throws ApplicationException {


        SavingAccountEntity savingAccountEntity = null;

        if(savingAccount != null) {

            savingAccountEntity = new SavingAccountEntity();

            savingAccountEntity.setSavingId(savingAccount.getId());
            savingAccountEntity.setSavingName(savingAccount.getName());
            savingAccountEntity.setSavingAmount(savingAccount.getAmount());
            savingAccountEntity.setSavingMinimum(savingAccount.getMinimum());
            savingAccountEntity.setSavingInterest(savingAccount.getInterest());
            savingAccountEntity.setClientByClientId(clientEntity);

        }
        else {
            throw new ApplicationException("Saving Account does not exist");
        }

        return savingAccountEntity;
    }


    private SavingAccount parseSavingAccountEntity(SavingAccountEntity savingAccountEntity)  throws ApplicationException {

        SavingAccount savingAccount = null;

        if(savingAccountEntity != null) {

            savingAccount = new SavingAccount();

            savingAccount.setId(savingAccountEntity.getSavingId());
            savingAccount.setName(savingAccountEntity.getSavingName());
            savingAccount.setAmount(savingAccountEntity.getSavingAmount());
            savingAccount.setMinimum(savingAccountEntity.getSavingMinimum());
            savingAccount.setInterest(savingAccountEntity.getSavingInterest());
            savingAccount.setClientId(savingAccountEntity.getClientByClientId().getId());
        }
        else {
            throw new ApplicationException("Saving Account does not exist");
        }

        return savingAccount;
    }
}
