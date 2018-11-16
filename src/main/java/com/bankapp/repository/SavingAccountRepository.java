package com.bankapp.repository;

import com.bankapp.dao.ClientEntity;
import com.bankapp.dao.SavingAccountEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SavingAccountRepository extends CrudRepository<SavingAccountEntity, Integer> {

    List<SavingAccountEntity> findByClientByClientId(ClientEntity client);
    // void removeByClientByClientId(ClientEntity client);
    void deleteByClientByClientId(ClientEntity clientEntity);
}
