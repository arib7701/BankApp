package com.bankapp.service.impl;

import com.bankapp.dao.ClientEntity;
import com.bankapp.dao.SavingAccountEntity;
import com.bankapp.dao.SpendingAccountEntity;
import com.bankapp.exception.ApplicationException;
import com.bankapp.model.SpendingAccount;
import com.bankapp.repository.SpendingAccountRepository;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

import com.bankapp.repository.ClientRepository;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SpendingAccountServiceImplTest {

    @InjectMocks
    SpendingAccountServiceImpl spendingService;

    @Mock
    SpendingAccountRepository spendingAccountRepository;

    @Mock
    ClientRepository clientRepository;

    @Test
    public void findById_whenSpendingAccountNull_expectedNull() throws ApplicationException {

        //GIVEN
        when(spendingAccountRepository.findById(any(int.class))).thenReturn(Optional.empty());

        try {
            //WHEN
            SpendingAccount realResponse = spendingService.findById(0);
        }
        catch (ApplicationException e) {

            //THEN
            assertTrue("Spending account does not exist !".equals(e.getMessage()));
        }
    }

    @Test
    public void save_WhenAccountExists_ExpectedNull() throws ApplicationException {

        // GIVEN
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setFirstname("Harry");
        clientEntity.setLastname("Boo");
        clientEntity.setPhone("0908070605");
        clientEntity.setEmail("hariboo@gmail.com");

        SpendingAccountEntity spendingAccountEntity = new SpendingAccountEntity();
        spendingAccountEntity.setSpendingName("Spending");
        spendingAccountEntity.setSpendingAmount(BigDecimal.valueOf(6000).movePointLeft(2));
        spendingAccountEntity.setSpendingCredit(BigDecimal.valueOf(500).movePointLeft(2));
        spendingAccountEntity.setClientByClientId(clientEntity);

        clientEntity.setSpendingAccountsById(spendingAccountEntity);

        SpendingAccount spendingAccount = new SpendingAccount();
        spendingAccount.setName("Spending");
        spendingAccount.setAmount(BigDecimal.valueOf(6000).movePointLeft(2));
        spendingAccount.setCredit(BigDecimal.valueOf(500).movePointLeft(2));
        spendingAccount.setClientId(1);

        when(clientRepository.findById(any(int.class))).thenReturn(Optional.of(clientEntity));

        //WHEN
        SpendingAccount response = spendingService.save(spendingAccount);

        //THEN
        assertTrue(response == null);
    }

    @Test
    public void save_WhenClientExistsAccountNull_ExpectedAccount() throws ApplicationException {

        // GIVEN
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setFirstname("Harry");
        clientEntity.setLastname("Boo");
        clientEntity.setPhone("0908070605");
        clientEntity.setEmail("hariboo@gmail.com");

        SpendingAccountEntity spendingAccountEntity = new SpendingAccountEntity();
        spendingAccountEntity.setSpendingName("Spending");
        spendingAccountEntity.setSpendingAmount(BigDecimal.valueOf(6000).movePointLeft(2));
        spendingAccountEntity.setSpendingCredit(BigDecimal.valueOf(500).movePointLeft(2));
        spendingAccountEntity.setClientByClientId(clientEntity);

        SpendingAccount spendingAccount = new SpendingAccount();
        spendingAccount.setName("Spending");
        spendingAccount.setAmount(BigDecimal.valueOf(6000).movePointLeft(2));
        spendingAccount.setCredit(BigDecimal.valueOf(500).movePointLeft(2));
        spendingAccount.setClientId(1);

        when(clientRepository.findById(any(int.class))).thenReturn(Optional.of(clientEntity));
        when(spendingAccountRepository.save(any(SpendingAccountEntity.class))).thenReturn(spendingAccountEntity);

        //WHEN
        SpendingAccount response = spendingService.save(spendingAccount);

        //THEN
        assertTrue(response != null && response.getClientId() == 1);
    }



    @Test
    public void updateSpendingAccount_WhenAccountExists_ExpectedAccount() throws ApplicationException {

        //GIVEN
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setFirstname("Hari");
        clientEntity.setLastname("Bo");
        clientEntity.setEmail("hari@bo.com");
        clientEntity.setPhone("5454545454");

        SpendingAccountEntity spendingAccountEntity =new SpendingAccountEntity();
        spendingAccountEntity.setSpendingName("Spending");
        spendingAccountEntity.setSpendingAmount(BigDecimal.valueOf(4000).movePointLeft(2));
        spendingAccountEntity.setSpendingCredit(BigDecimal.valueOf(300).movePointLeft(2));
        spendingAccountEntity.setClientByClientId(clientEntity);

        SpendingAccount spendingAccount = new SpendingAccount();
        spendingAccount.setName("Spending");
        spendingAccount.setAmount(BigDecimal.valueOf(4000).movePointLeft(2));
        spendingAccount.setCredit(BigDecimal.valueOf(100).movePointLeft(2));
        spendingAccount.setClientId(1);

        when(spendingAccountRepository.findById(any(int.class))).thenReturn(Optional.of(spendingAccountEntity));
        when(spendingAccountRepository.save(spendingAccountEntity)).thenReturn(spendingAccountEntity);

        //WHEN
        SpendingAccount response = spendingService.updateSpendingAccount(1, spendingAccount);

        //THEN
        assertTrue(response != null && response.getClientId() == 1);
        
    }


    @Test
    public void updateSpendingAccount_WhenAccountNull_ExpectedNull() throws ApplicationException {

        //GIVEN
        SpendingAccount spendingAccount = new SpendingAccount();
        spendingAccount.setClientId(1);
        spendingAccount.setName("Spending");
        spendingAccount.setAmount(BigDecimal.valueOf(2000).movePointLeft(2));
        spendingAccount.setCredit(BigDecimal.valueOf(200).movePointLeft(2));
        spendingAccount.setClientId(1);

        when(spendingAccountRepository.findById(any(int.class))).thenReturn(Optional.empty());

        try {
            //WHEN
            SpendingAccount response = spendingService.updateSpendingAccount(1, spendingAccount);
        }
        catch (ApplicationException e) {

            //THEN
            assertTrue("Spending account does not exist !".equals(e.getMessage()));
        }
    }

    @Test
    public void removeById_WhenAccountNull_ExpectedFalse() throws ApplicationException {

        try {
            //WHEN
            boolean realResponse = spendingService.removeById(0);
            verify(spendingAccountRepository, times(0)).deleteById(0);
        }
        catch (ApplicationException e) {

            //THEN
            assertTrue("Spending account does not exist !".equals(e.getMessage()));
        }
    }

    @Test
    public void removeById_WhenAccountExists_ExpectedTrue() throws ApplicationException {

        //GIVEN
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setFirstname("Hari");
        clientEntity.setLastname("Bo");
        clientEntity.setEmail("hari@bo.com");
        clientEntity.setPhone("4545454545");
        clientEntity.setSavingAccountsById(new ArrayList<SavingAccountEntity>());

        SpendingAccountEntity spendingAccountEntity =new SpendingAccountEntity();
        spendingAccountEntity.setSpendingId(1);
        spendingAccountEntity.setSpendingName("Spending");
        spendingAccountEntity.setSpendingAmount(BigDecimal.valueOf(4000).movePointLeft(2));
        spendingAccountEntity.setSpendingCredit(BigDecimal.valueOf(300).movePointLeft(2));
        spendingAccountEntity.setClientByClientId(clientEntity);

        when(spendingAccountRepository.findById(any(int.class))).thenReturn(Optional.of(spendingAccountEntity));
        when(clientRepository.findById(any(int.class))).thenReturn(Optional.of(clientEntity));

        //WHEN
        boolean realResponse = spendingService.removeById(1);

        //THEN
        verify(spendingAccountRepository, times(1)).deleteById(1);
        assertTrue(realResponse);
    }

    @Test
    public void removeByClientId_WhenClientNull_ExpectedFalse() throws ApplicationException {

        //GIVEN
        when(clientRepository.findById(any(int.class))).thenReturn(Optional.empty());

        try {
            //WHEN
            boolean realResponse = spendingService.removeByClientId(1);
            verify(spendingAccountRepository, times(0)).removeByClientByClientId(null);
        }
        catch (ApplicationException e) {

            //THEN
            assertTrue("Client does not exist !".equals(e.getMessage()));
        }

    }

    @Test
    public void removeByClientId_WhenClientExists_ExpectedTrue() throws ApplicationException {

        //GIVEN
        ClientEntity clientEntity =new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setFirstname("Hari");
        clientEntity.setLastname("Bo");
        clientEntity.setEmail("hari@bo.com");
        clientEntity.setPhone("4545454545");
        clientEntity.setSavingAccountsById(new ArrayList<SavingAccountEntity>());

        when(clientRepository.findById(any(int.class))).thenReturn(Optional.of(clientEntity));

        //WHEN
        boolean realResponse = spendingService.removeByClientId(1);
        verify(spendingAccountRepository, times(1)).removeByClientByClientId(clientEntity);

        //THEN
        assert(realResponse);

    }
}