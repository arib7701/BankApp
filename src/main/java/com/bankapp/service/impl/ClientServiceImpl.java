package com.bankapp.service.impl;

import com.bankapp.exception.ApplicationException;
import com.bankapp.repository.ClientRepository;
import com.bankapp.repository.SavingAccountRepository;
import com.bankapp.repository.SpendingAccountRepository;
import com.bankapp.dao.ClientEntity;
import com.bankapp.dao.SavingAccountEntity;
import com.bankapp.dao.SpendingAccountEntity;
import com.bankapp.model.Account;
import com.bankapp.model.Client;
import com.bankapp.model.SavingAccount;
import com.bankapp.model.SpendingAccount;
import com.bankapp.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SpendingAccountRepository spendingAccountRepository;

    @Autowired
    private SavingAccountRepository savingAccountRepository;

    @Override
    public List<Client> findAll() throws ApplicationException {

        List<ClientEntity> clientListEntity  = (List<ClientEntity>) clientRepository.findAll();

        List<Client> clients = null;

        if(clientListEntity != null) {

            clients = new ArrayList<>();

            for (ClientEntity ce : clientListEntity) {

                Client client = parseClientEntity(ce);
                client.setAccounts(null);

                clients.add(client);
            }
        }
        else {
            throw new ApplicationException("Client does not exist");
        }

        return clients;
    }

    @Override
    public Client findById(int id) throws ApplicationException {

        Client newClient = null;
        ClientEntity ce = clientRepository.findById(id).orElse(null);

        if(ce != null) {

            newClient = parseClientEntity(ce);

            if(ce.getSpendingAccountsById() != null) {

                List<Account> accounts = new ArrayList<>();

                SpendingAccount spdAcc = new SpendingAccount();

                spdAcc.setId(ce.getSpendingAccountsById().getSpendingId());
                spdAcc.setName(ce.getSpendingAccountsById().getSpendingName());
                spdAcc.setAmount(ce.getSpendingAccountsById().getSpendingAmount());
                spdAcc.setClientId(ce.getId());
                spdAcc.setCredit(ce.getSpendingAccountsById().getSpendingCredit().multiply(BigDecimal.valueOf(-1)));

                accounts.add(spdAcc);

                if(ce.getSavingAccountsById() != null) {

                    for (SavingAccountEntity se : ce.getSavingAccountsById()) {

                        SavingAccount svAcc = new SavingAccount();
                        svAcc.setId(se.getSavingId());
                        svAcc.setName(se.getSavingName());
                        svAcc.setAmount(se.getSavingAmount());
                        svAcc.setClientId(ce.getId());
                        svAcc.setInterest(se.getSavingInterest());
                        svAcc.setMinimum(se.getSavingMinimum());

                        accounts.add(svAcc);
                    }
                }

                newClient.setAccounts(accounts);
            }
        }
        else {
            throw new ApplicationException("Client does not exist");
        }

        return newClient;
    }

    @Override
    public Client save(Client clientBody) throws ApplicationException {

        ClientEntity clientEntity = parseClient(clientBody);

        ClientEntity CE = clientRepository.save(clientEntity);

        Client newClient = parseClientEntity(CE);

        return newClient;
    }

    @Override
    public Client update(int id, Client client) throws ApplicationException {

        ClientEntity ce = clientRepository.findById(id).orElse(null);
        Client updatedClient = null;

        if(ce != null) {

            ce.setEmail(client.getEmail());
            ce.setPhone(client.getPhone());

            ClientEntity clientEntity = clientRepository.save(ce);

            updatedClient = parseClientEntity(clientEntity);
        }
        else {
            throw new ApplicationException("Client does not exist");
        }

        return updatedClient;
    }

    @Override
    public boolean removeById(int id) throws ApplicationException {

        boolean deleted;

        ClientEntity clientEntity = clientRepository.findById(id).orElse(null);

        if(clientEntity != null){

            List<SavingAccountEntity> savingAccountEntities = savingAccountRepository.findByClientByClientId(clientEntity);

            if(savingAccountEntities.size() > 0){
                for(SavingAccountEntity se : savingAccountEntities){
                    savingAccountRepository.delete(se);
                }
            }

            SpendingAccountEntity spendingAccountEntity = spendingAccountRepository.findByClientByClientId(clientEntity);

            if( spendingAccountEntity != null){
                spendingAccountRepository.delete(spendingAccountEntity);
            }

            clientRepository.deleteById(id);
            deleted = true;
        }
        else {
            throw new ApplicationException("Client does not exist");
        }

        return deleted;
    }


    @Override
    public Client findByEmail(String email) throws ApplicationException {

        Client client = null;
        ClientEntity ce = clientRepository.findByEmail(email);

        if(ce != null) {

            client = parseClientEntity(ce);

            if(ce.getSpendingAccountsById() != null) {

                List<Account> accounts = new ArrayList<>();

                SpendingAccount spdAcc = new SpendingAccount();

                spdAcc.setId(ce.getSpendingAccountsById().getSpendingId());
                spdAcc.setName(ce.getSpendingAccountsById().getSpendingName());
                spdAcc.setAmount(ce.getSpendingAccountsById().getSpendingAmount());
                spdAcc.setClientId(ce.getId());
                spdAcc.setCredit(ce.getSpendingAccountsById().getSpendingCredit().multiply(BigDecimal.valueOf(-1)));

                accounts.add(spdAcc);

                if(ce.getSavingAccountsById() != null) {

                    for (SavingAccountEntity se : ce.getSavingAccountsById()) {

                        SavingAccount svAcc = new SavingAccount();
                        svAcc.setId(se.getSavingId());
                        svAcc.setName(se.getSavingName());
                        svAcc.setAmount(se.getSavingAmount());
                        svAcc.setClientId(ce.getId());
                        svAcc.setInterest(se.getSavingInterest());
                        svAcc.setMinimum(se.getSavingMinimum());

                        accounts.add(svAcc);
                    }
                }

                client.setAccounts(accounts);
            }
        }
        else {
            throw new ApplicationException("Client does not exist");
        }

        return client;
     }


    @Override
    public boolean removeByEmail(String email) throws ApplicationException {

        boolean deleted;

        ClientEntity clientEntity = clientRepository.findByEmail(email);

        if(clientEntity != null){

            SpendingAccountEntity spendingAccountEntity = spendingAccountRepository.findByClientByClientId(clientEntity);

            if( spendingAccountEntity != null){
                spendingAccountRepository.removeByClientByClientId(clientEntity);
            }

            List<SavingAccountEntity> savingAccountEntities = savingAccountRepository.findByClientByClientId(clientEntity);

            if(savingAccountEntities.size() > 0){
                savingAccountRepository.deleteByClientByClientId(clientEntity);
            }

            clientRepository.deleteByEmail(email);
            deleted = true;
        }
        else {
            throw new ApplicationException("Client does not exist");
        }

        return deleted;
    }

    private ClientEntity parseClient (Client client) throws ApplicationException {

        ClientEntity clientEntity = null;

        if(client != null) {

            clientEntity = new ClientEntity();
            clientEntity.setId(client.getId());
            clientEntity.setFirstname(client.getFirstname());
            clientEntity.setLastname(client.getLastname());
            clientEntity.setPhone(client.getPhone());
            clientEntity.setEmail((client.getEmail()));
        }
        else {
            throw new ApplicationException("Client does not exist");
        }

        return clientEntity;

    }


    private Client parseClientEntity ( ClientEntity clientEntity) throws ApplicationException {

        Client client = null;

        if(clientEntity != null) {

            client = new Client();
            client.setId(clientEntity.getId());
            client.setFirstname(clientEntity.getFirstname());
            client.setLastname(clientEntity.getLastname());
            client.setPhone(clientEntity.getPhone());
            client.setEmail(clientEntity.getEmail());
        }
        else {
            throw new ApplicationException("Client does not exist");
        }

        return client;

    }
}
