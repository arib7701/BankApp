package com.bankapp.repository;

import com.bankapp.dao.ClientEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<ClientEntity, Integer> {

    ClientEntity findByEmail(String email);
    void deleteByEmail(String email);

}

