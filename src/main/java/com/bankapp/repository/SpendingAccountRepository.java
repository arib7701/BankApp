package com.bankapp.repository;

import com.bankapp.dao.ClientEntity;
import com.bankapp.dao.SpendingAccountEntity;
import org.springframework.data.repository.CrudRepository;

public interface SpendingAccountRepository extends CrudRepository<SpendingAccountEntity, Integer> {

        SpendingAccountEntity findByClientByClientId(ClientEntity client);
        void removeByClientByClientId(ClientEntity client);
}


