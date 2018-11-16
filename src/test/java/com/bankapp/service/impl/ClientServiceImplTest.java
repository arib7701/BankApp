package com.bankapp.service.impl;

import com.bankapp.dao.ClientEntity;
import com.bankapp.dao.SavingAccountEntity;
import com.bankapp.dao.SpendingAccountEntity;
import com.bankapp.exception.ApplicationException;
import com.bankapp.model.Client;
import com.bankapp.repository.ClientRepository;
import com.bankapp.repository.SavingAccountRepository;
import com.bankapp.repository.SpendingAccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientServiceImplTest {

    @InjectMocks
    ClientServiceImpl clientService;

    @Mock
    ClientRepository clientRepository;

    @Mock
    SpendingAccountRepository spendingAccountRepository;

    @Mock
    SavingAccountRepository savingAccountRepository;

    @Test
    public void findAll_WhenNoClient_ExpectedNull() throws ApplicationException {

        // GIVEN
        when(clientRepository.findAll()).thenReturn(null);

        try {
            // WHEN
            List<Client> realResponse = clientService.findAll();
        }
        catch (ApplicationException e) {

            // THEN
            assertTrue("Client does not exist".equals(e.getMessage()));
        }
    }

    @Test
    public void findAll_WhenClientsExist_ExpectedClients() throws ApplicationException {

        // GIVEN
        ClientEntity clientEntity1 = new ClientEntity();
        clientEntity1.setId(1);
        clientEntity1.setFirstname("Harry");
        clientEntity1.setLastname("Boo");
        clientEntity1.setPhone("0908070605");
        clientEntity1.setEmail("hariboo@gmail.com");

        ClientEntity clientEntity2 = new ClientEntity();
        clientEntity2.setId(2);
        clientEntity2.setFirstname("Harriette");
        clientEntity2.setLastname("Boole");
        clientEntity2.setPhone("0908099905");
        clientEntity2.setEmail("harietteboole@gmail.com");

        List<ClientEntity> allClients = new ArrayList<>();
        allClients.add(clientEntity1);
        allClients.add(clientEntity2);

        when(clientRepository.findAll()).thenReturn(allClients);

        // WHEN
        List<Client> realResponse = clientService.findAll();

        // THEN
        assertEquals(2, realResponse.size());
    }

    @Test
    public void findById_WhenNotExists_ExpectedNull() throws ApplicationException {

        // GIVEN
        when(clientRepository.findById(any(int.class))).thenReturn(Optional.empty());

        try {
            // WHEN
            Client realResponse = clientService.findById(0);
        }
        catch (ApplicationException e) {

            // THEN
            assertTrue("Client does not exist".equals(e.getMessage()));
        }
    }

    @Test
    public void findById_WhenExists_ExpectedClient() throws ApplicationException {

        // GIVEN
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setFirstname("Harry");
        clientEntity.setLastname("Boo");
        clientEntity.setPhone("0908070605");
        clientEntity.setEmail("hariboo@gmail.com");

        when(clientRepository.findById(any(int.class))).thenReturn(Optional.of(clientEntity));

        // WHEN
        Client realResponse = clientService.findById(1);

        // THEN
        assertTrue(realResponse != null && realResponse.getId() == 1 && realResponse.getFirstname() ==  "Harry");
    }

    @Test
    public void save_ExpectedClient() throws ApplicationException {

        // GIVEN
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setFirstname("Harry");
        clientEntity.setLastname("Boo");
        clientEntity.setPhone("0908070605");
        clientEntity.setEmail("hariboo@gmail.com");

        Client client = new Client();
        client.setFirstname("Harry");
        client.setLastname("Boo");
        client.setPhone("0908070605");
        client.setEmail("hariboo@gmail.com");

        when(clientRepository.save(clientEntity)).thenReturn(clientEntity);

        // WHEN
        Client realResponse = clientService.save(client);

        // THEN
        assertTrue(realResponse != null && realResponse.getFirstname() == "Harry");
    }

    @Test
    public void update_WhenClientNotExist_ExpectedNull() throws ApplicationException {

        // GIVEN
        Client client = new Client();
        client.setId(1);
        client.setFirstname("Henry");
        client.setLastname("Boo");
        client.setPhone("0908070605");
        client.setEmail("hariboo@gmail.com");

        when(clientRepository.findById(any(int.class))).thenReturn(Optional.empty());

        try {
            // WHEN
            Client realResponse = clientService.update(1, client);
        }
        catch( ApplicationException e) {

            // THEN
            assertTrue("Client does not exist".equals(e.getMessage()));
        }
    }

    @Test
    public void update_WhenClientExist_ExpectedClient() throws ApplicationException {

        // GIVEN
        Client client = new Client();
        client.setId(1);
        client.setFirstname("Henry");
        client.setLastname("Boo");
        client.setPhone("0908070605");
        client.setEmail("hariboo@gmail.com");

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setFirstname("Harry");
        clientEntity.setLastname("Boo");
        clientEntity.setPhone("0908070605");
        clientEntity.setEmail("hariboo@gmail.com");

        when(clientRepository.findById(any(int.class))).thenReturn(Optional.of(clientEntity));
        when(clientRepository.save(clientEntity)).thenReturn(clientEntity);

        // WHEN
        Client realResponse = clientService.update(1, client);

        // THEN
        assertTrue(realResponse != null && realResponse.getId() == 1 && realResponse.getFirstname() == "Harry");
    }

    @Test
    public void removeById_WhenClientNotExist_ExpectedNotCalling() throws Exception {

        // GIVEN
        when(clientRepository.findById(any(int.class))).thenReturn(Optional.empty());

        try {
            // WHEN
            clientService.removeById(1);

            // THEN
            verify(clientRepository, times(0)).deleteById(0);
        }
        catch (Exception e) {
            assertTrue("Client does not exist".equals(e.getMessage()));
        }
    }

    @Test
    public void removeById_WhenClientExist_ExpectedCallingOnce() throws Exception {

        // GIVEN
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setFirstname("Harry");
        clientEntity.setLastname("Boo");
        clientEntity.setPhone("0908070605");
        clientEntity.setEmail("hariboo@gmail.com");

        SpendingAccountEntity spendingAccountEntity = new SpendingAccountEntity();
        spendingAccountEntity.setSpendingId(1);
        spendingAccountEntity.setSpendingName("Compte courant");
        spendingAccountEntity.setSpendingAmount(BigDecimal.valueOf(5000).movePointLeft(2));
        spendingAccountEntity.setSpendingCredit(BigDecimal.valueOf(300).movePointLeft(2));
        spendingAccountEntity.setClientByClientId(clientEntity);

        SavingAccountEntity savingAccountEntity1 = new SavingAccountEntity();
        savingAccountEntity1.setSavingId(1);
        savingAccountEntity1.setSavingName("saving");
        savingAccountEntity1.setSavingAmount(BigDecimal.valueOf(2000).movePointLeft(2));
        savingAccountEntity1.setSavingMinimum(BigDecimal.valueOf(20).movePointLeft(2));
        savingAccountEntity1.setSavingInterest(BigDecimal.valueOf(2.0).movePointLeft(2));
        savingAccountEntity1.setClientByClientId(clientEntity);

        SavingAccountEntity savingAccountEntity2 = new SavingAccountEntity();
        savingAccountEntity2.setSavingId(2);
        savingAccountEntity2.setSavingName("saving");
        savingAccountEntity2.setSavingAmount(BigDecimal.valueOf(2000).movePointLeft(2));
        savingAccountEntity2.setSavingMinimum(BigDecimal.valueOf(20).movePointLeft(2));
        savingAccountEntity2.setSavingInterest(BigDecimal.valueOf(2.0).movePointLeft(2));
        savingAccountEntity2.setClientByClientId(clientEntity);

        List<SavingAccountEntity> savingAccountEntities = new ArrayList<>();
        savingAccountEntities.add(savingAccountEntity1);
        savingAccountEntities.add(savingAccountEntity2);

        when(clientRepository.findById(any(int.class))).thenReturn(Optional.of(clientEntity));
        when(spendingAccountRepository.findByClientByClientId(any(ClientEntity.class))).thenReturn(spendingAccountEntity);
        when(savingAccountRepository.findByClientByClientId(any(ClientEntity.class))).thenReturn(savingAccountEntities);


        try {
            // WHEN
            clientService.removeById(1);
            verify(clientRepository, times(1)).deleteById(1);
            verify(spendingAccountRepository, times(1)).delete(spendingAccountEntity);
            verify(savingAccountRepository, times(1)).delete(savingAccountEntity1);
            verify(savingAccountRepository, times(1)).delete(savingAccountEntity2);
        }
        catch (Exception e) {
            // THEN
            assertTrue("Client does not exist".equals(e.getMessage()));
        }
    }

    @Test
    public void findByEmail_WhenClientNotExists_ExpectedNull() throws ApplicationException {

        // GIVEN
        when(clientRepository.findByEmail(any(String.class))).thenReturn(null);

        try {
            // WHEN
            Client realResponse = clientService.findByEmail("");

        } catch (ApplicationException e) {

            // THEN
            assertTrue("Client does not exist".equals(e.getMessage()));
        }
    }

    @Test
    public void findByEmail_WhenClientExists_ExpectedClient() throws ApplicationException {

        // GIVEN
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setFirstname("Harry");
        clientEntity.setLastname("Boo");
        clientEntity.setPhone("0908070605");
        clientEntity.setEmail("hariboo@gmail.com");

        when(clientRepository.findByEmail(any(String.class))).thenReturn(clientEntity);

        // WHEN
        Client realResponse = clientService.findByEmail("hariboo@gmail.com");

        // THEN
        assertTrue(realResponse != null && realResponse.getId() == 1 && realResponse.getEmail() == "hariboo@gmail.com");
    }

    @Test
    public void removeByEmail_WhenClientNotExist_ExpectedNotCalling() throws ApplicationException {

        // GIVEN
        when(clientRepository.findByEmail(any(String.class))).thenReturn(null);

        try {
            // WHEN
            clientService.removeByEmail("");

            // THEN
            verify(clientRepository, times(0)).deleteByEmail("");
        }
        catch (Exception e) {
            assertTrue("Client does not exist".equals(e.getMessage()));
        }
    }

    @Test
    public void removeByEmail_WhenClientExist_ExpectedCallingOnce() throws ApplicationException {

        // GIVEN
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setFirstname("Harry");
        clientEntity.setLastname("Boo");
        clientEntity.setPhone("0908070605");
        clientEntity.setEmail("hariboo@gmail.com");

        SpendingAccountEntity spendingAccountEntity = new SpendingAccountEntity();
        spendingAccountEntity.setSpendingId(1);
        spendingAccountEntity.setSpendingName("Compte courant");
        spendingAccountEntity.setSpendingAmount(BigDecimal.valueOf(5000).movePointLeft(2));
        spendingAccountEntity.setSpendingCredit(BigDecimal.valueOf(300).movePointLeft(2));
        spendingAccountEntity.setClientByClientId(clientEntity);

        SavingAccountEntity savingAccountEntity1 = new SavingAccountEntity();
        savingAccountEntity1.setSavingId(1);
        savingAccountEntity1.setSavingName("saving");
        savingAccountEntity1.setSavingAmount(BigDecimal.valueOf(2000).movePointLeft(2));
        savingAccountEntity1.setSavingMinimum(BigDecimal.valueOf(20).movePointLeft(2));
        savingAccountEntity1.setSavingInterest(BigDecimal.valueOf(2.0).movePointLeft(2));
        savingAccountEntity1.setClientByClientId(clientEntity);

        SavingAccountEntity savingAccountEntity2 = new SavingAccountEntity();
        savingAccountEntity2.setSavingId(2);
        savingAccountEntity2.setSavingName("saving");
        savingAccountEntity2.setSavingAmount(BigDecimal.valueOf(2000).movePointLeft(2));
        savingAccountEntity2.setSavingMinimum(BigDecimal.valueOf(20).movePointLeft(2));
        savingAccountEntity2.setSavingInterest(BigDecimal.valueOf(2.0).movePointLeft(2));
        savingAccountEntity2.setClientByClientId(clientEntity);

        List<SavingAccountEntity> savingAccountEntities = new ArrayList<>();
        savingAccountEntities.add(savingAccountEntity1);
        savingAccountEntities.add(savingAccountEntity2);

        when(clientRepository.findByEmail(any(String.class))).thenReturn(clientEntity);
        when(spendingAccountRepository.findByClientByClientId(any(ClientEntity.class))).thenReturn(spendingAccountEntity);
        when(savingAccountRepository.findByClientByClientId(any(ClientEntity.class))).thenReturn(savingAccountEntities);


        try {
            // WHEN
            clientService.removeByEmail("hariboo@gmail.com");
            verify(clientRepository, times(1)).deleteByEmail("hariboo@gmail.com");
            verify(spendingAccountRepository, times(1)).removeByClientByClientId(clientEntity);
            verify(savingAccountRepository, times(1)).deleteByClientByClientId(clientEntity);
        }
        catch (Exception e) {
            // THEN
            assertTrue("Client does not exist".equals(e.getMessage()));
        }
    }
}