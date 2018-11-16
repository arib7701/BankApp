package com.bankapp.service;

import com.bankapp.exception.ApplicationException;
import com.bankapp.model.Client;

import java.util.List;

public interface IClientService {

    List<Client> findAll() throws ApplicationException;
    Client findById(int id) throws  ApplicationException;
    Client save(Client client) throws ApplicationException;
    boolean removeById(int id) throws ApplicationException;
    Client findByEmail(String email) throws ApplicationException;
    boolean removeByEmail (String email) throws ApplicationException;
    Client update(int id, Client client) throws ApplicationException;

}