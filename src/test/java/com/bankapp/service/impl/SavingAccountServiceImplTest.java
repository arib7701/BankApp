package com.bankapp.service.impl;

import com.bankapp.dao.ClientEntity;
import com.bankapp.dao.SavingAccountEntity;
import com.bankapp.dao.SpendingAccountEntity;
import com.bankapp.exception.ApplicationException;
import com.bankapp.model.SavingAccount;
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

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;


@RunWith(MockitoJUnitRunner.class)
public class SavingAccountServiceImplTest {

    @InjectMocks
    private SavingAccountServiceImpl savingAccountService;

    @Mock
    private SavingAccountRepository savingAccountRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private SpendingAccountRepository spendingAccountRepository;

    @Test
    public void findAll_WhenAccountsNull_ExpectedNull() throws ApplicationException {

        // GIVEN
        when(savingAccountRepository.findAll()).thenReturn(null);

        try {
            // WHEN
            List<SavingAccount> realResponse = savingAccountService.findAll();
        }
        catch (ApplicationException e) {

            // THEN
            assertTrue("Saving Accounts do not exist".equals(e.getMessage()));
        }

    }

    @Test
    public void findAll_WhenExist_ExpectedAccountList() throws ApplicationException {

        // GIVEN

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setFirstname("Harry");
        clientEntity.setLastname("Boo");
        clientEntity.setPhone("0908070605");
        clientEntity.setEmail("hariboo@gmail.com");

        List<SavingAccountEntity> savingAccounts = new ArrayList<>();

        SavingAccountEntity saving1 = new SavingAccountEntity();
        saving1.setSavingId(1);
        saving1.setSavingName("Saving");
        saving1.setSavingAmount(BigDecimal.valueOf(20000).movePointLeft(2));
        saving1.setSavingMinimum(BigDecimal.valueOf(20).movePointLeft(2));
        saving1.setSavingInterest(BigDecimal.valueOf(1.2).movePointLeft(2));
        saving1.setClientByClientId(clientEntity);

        savingAccounts.add(saving1);

        SavingAccountEntity saving2 = new SavingAccountEntity();
        saving2.setSavingId(2);
        saving2.setSavingName("Money saved");
        saving2.setSavingAmount(BigDecimal.valueOf(10000).movePointLeft(2));
        saving2.setSavingMinimum(BigDecimal.valueOf(40).movePointLeft(2));
        saving2.setSavingInterest(BigDecimal.valueOf(2.2).movePointLeft(2));
        saving2.setClientByClientId(clientEntity);

        savingAccounts.add(saving2);

        when(savingAccountRepository.findAll()).thenReturn(savingAccounts);


        // WHEN
        List<SavingAccount> realResponse = savingAccountService.findAll();

        // THEN
        assertTrue(realResponse.size() == 2
                && realResponse.get(0).getId() == 1
                && realResponse.get(0).getClientId() == 1
                && realResponse.get(1).getId() == 2
                && realResponse.get(1).getClientId() == 1);
    }

    @Test
    public void findAllByClientId_WhenClientNull_ExpectedNull() throws ApplicationException {

        try {
            // WHEN
            List<SavingAccount> realResponse = savingAccountService.findAllByClientId(0);
        }
        catch (ApplicationException e) {

            // THEN
            assertTrue("Client does not exist".equals(e.getMessage()));
        }
    }

    @Test
    public void findAllByClientId_WhenAccountNull_ExpectedNull() throws ApplicationException {

        // GIVEN Client & ClientEntity

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setFirstname("Harry");
        clientEntity.setLastname("Boo");
        clientEntity.setPhone("0908070605");
        clientEntity.setEmail("hariboo@gmail.com");

        when(clientRepository.findById(any(int.class))).thenReturn(Optional.of(clientEntity));
        when(savingAccountRepository.findByClientByClientId(clientEntity)).thenReturn(null);

        try {
            // WHEN
            List<SavingAccount> realResponse = savingAccountService.findAllByClientId(1);
        }
        catch (ApplicationException e) {
            // THEN
            assertTrue("Saving Accounts do not exist".equals(e.getMessage()));
        }
    }

    @Test
    public void findAllByClientId_WhenExist_ExpectedListAccounts() throws ApplicationException {

        // GIVEN
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setFirstname("Harry");
        clientEntity.setLastname("Boo");
        clientEntity.setPhone("0908070605");
        clientEntity.setEmail("hariboo@gmail.com");

        List<SavingAccountEntity> savingAccounts = new ArrayList<>();

        SavingAccountEntity saving1 = new SavingAccountEntity();
        saving1.setSavingId(1);
        saving1.setSavingName("Saving");
        saving1.setSavingAmount(BigDecimal.valueOf(20000).movePointLeft(2));
        saving1.setSavingMinimum(BigDecimal.valueOf(20).movePointLeft(2));
        saving1.setSavingInterest(BigDecimal.valueOf(1.2).movePointLeft(2));
        saving1.setClientByClientId(clientEntity);

        savingAccounts.add(saving1);

        SavingAccountEntity saving2 = new SavingAccountEntity();
        saving2.setSavingId(2);
        saving2.setSavingName("Money saved");
        saving2.setSavingAmount(BigDecimal.valueOf(10000).movePointLeft(2));
        saving2.setSavingMinimum(BigDecimal.valueOf(40).movePointLeft(2));
        saving2.setSavingInterest(BigDecimal.valueOf(2.2).movePointLeft(2));
        saving2.setClientByClientId(clientEntity);

        savingAccounts.add(saving2);

        when(clientRepository.findById(any(int.class))).thenReturn(Optional.of(clientEntity));
        when(savingAccountRepository.findByClientByClientId(clientEntity)).thenReturn(savingAccounts);

        // WHEN
        List<SavingAccount> realResponse = savingAccountService.findAllByClientId(1);

        // THEN
        assertEquals(2, realResponse.size());
    }

    @Test
    public void findById_WhenAccountNull_ExpectedNull() throws ApplicationException {

        // GIVEN
        when(savingAccountRepository.findById(any(int.class))).thenReturn(Optional.empty());

        try {
            // WHEN
            SavingAccount realResponse = savingAccountService.findById(0);
        }
        catch (ApplicationException e) {

            // THEN
            assertTrue("Saving Account does not exist".equals(e.getMessage()));
        }
    }

    @Test
    public void findById_WhenExist_ExpectedAccount() throws ApplicationException {

        // GIVEN

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setFirstname("Harry");
        clientEntity.setLastname("Boo");
        clientEntity.setPhone("0908070605");
        clientEntity.setEmail("hariboo@gmail.com");

        SavingAccountEntity savingAccountEntity = new SavingAccountEntity();
        savingAccountEntity.setSavingId(1);
        savingAccountEntity.setSavingName("saving");
        savingAccountEntity.setSavingAmount(BigDecimal.valueOf(2000).movePointLeft(2));
        savingAccountEntity.setSavingMinimum(BigDecimal.valueOf(20).movePointLeft(2));
        savingAccountEntity.setSavingInterest(BigDecimal.valueOf(2.0).movePointLeft(2));
        savingAccountEntity.setClientByClientId(clientEntity);

        when(savingAccountRepository.findById(any(int.class))).thenReturn(Optional.of(savingAccountEntity));

        // WHEN
        SavingAccount realResponse = savingAccountService.findById(1);

        // THEN
        assertTrue(realResponse != null && realResponse.getId() == 1 && realResponse.getClientId() == 1);
    }

    @Test
    public void save_WhenClientNull_ExpectedNull() throws ApplicationException {

        // GIVEN
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(1);
        savingAccount.setName("saving");
        savingAccount.setAmount(BigDecimal.valueOf(200).movePointLeft(2));
        savingAccount.setMinimum(BigDecimal.valueOf(20).movePointLeft(2));
        savingAccount.setInterest(BigDecimal.valueOf(2.0).movePointLeft(2));
        savingAccount.setClientId(0);

        try {
            // WHEN
            SavingAccount realResponse = savingAccountService.save(savingAccount);
        }
        catch (ApplicationException e) {

            // THEN
            assertTrue("Client does not exist, can not save the account".equals(e.getMessage()));
        }
    }

    @Test
    public void save_WhenClientExists_ExpectedAccount() throws ApplicationException {

        // GIVEN
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setFirstname("Harry");
        clientEntity.setLastname("Boo");
        clientEntity.setPhone("0908070605");
        clientEntity.setEmail("hariboo@gmail.com");

        SavingAccountEntity savingAccountEntity = new SavingAccountEntity();
        savingAccountEntity.setSavingName("saving");
        savingAccountEntity.setSavingAmount(BigDecimal.valueOf(2000).movePointLeft(2));
        savingAccountEntity.setSavingMinimum(BigDecimal.valueOf(20).movePointLeft(2));
        savingAccountEntity.setSavingInterest(BigDecimal.valueOf(2.0).movePointLeft(2));
        savingAccountEntity.setClientByClientId(clientEntity);

        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setName("saving");
        savingAccount.setAmount(BigDecimal.valueOf(2000).movePointLeft(2));
        savingAccount.setMinimum(BigDecimal.valueOf(20).movePointLeft(2));
        savingAccount.setInterest(BigDecimal.valueOf(2.0).movePointLeft(2));
        savingAccount.setClientId(1);

        when(clientRepository.findById(any(int.class))).thenReturn(Optional.of(clientEntity));
        when(savingAccountRepository.save(savingAccountEntity)).thenReturn(savingAccountEntity);

        // WHEN
        SavingAccount realResponse = savingAccountService.save(savingAccount);

        // THEN
        assertTrue(realResponse != null && realResponse.getClientId() == 1);

    }

    @Test
    public void removeById_WhenAccountNull_ExpectedFalse() throws ApplicationException {

        try {
            // WHEN
            boolean realResponse = savingAccountService.removeById(0);
        }
        catch (ApplicationException e) {

            // THEN
            verify(savingAccountRepository, times(0)).deleteById(0);
            assertTrue("Saving Account does not exist".equals(e.getMessage()));
        }

    }

    @Test
    public void removeById_WhenAccountExist_ExpectedTrue() throws ApplicationException {

        // GIVEN
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setFirstname("Harry");
        clientEntity.setLastname("Boo");
        clientEntity.setPhone("0908070605");
        clientEntity.setEmail("hariboo@gmail.com");

        SpendingAccountEntity spendingAccountEntity = new SpendingAccountEntity();
        spendingAccountEntity.setSpendingId(1);
        spendingAccountEntity.setSpendingName("spending");
        spendingAccountEntity.setSpendingAmount(BigDecimal.valueOf(200).movePointLeft(2));
        spendingAccountEntity.setSpendingCredit(BigDecimal.valueOf(200).movePointLeft(2));

        SavingAccountEntity savingAccountEntity = new SavingAccountEntity();
        savingAccountEntity.setSavingName("saving");
        savingAccountEntity.setSavingAmount(BigDecimal.valueOf(2000).movePointLeft(2));
        savingAccountEntity.setSavingMinimum(BigDecimal.valueOf(20).movePointLeft(2));
        savingAccountEntity.setSavingInterest(BigDecimal.valueOf(2.0).movePointLeft(2));
        savingAccountEntity.setClientByClientId(clientEntity);

        when(savingAccountRepository.findById(any(int.class))).thenReturn(Optional.of(savingAccountEntity));
        when(spendingAccountRepository.findByClientByClientId(clientEntity)).thenReturn(spendingAccountEntity);
        when(spendingAccountRepository.save(spendingAccountEntity)).thenReturn(spendingAccountEntity);

        // WHEN
        boolean realResponse = savingAccountService.removeById(1);
        verify(savingAccountRepository, times(1)).deleteById(1);

        // THEN
        assertTrue(realResponse);

    }

    @Test
    public void removeByClientId_WhenClientNull_ExpectedFalse() throws ApplicationException {

        try {
            // WHEN
            boolean realResponse = savingAccountService.removeByClientId(0);
        } catch (ApplicationException e) {

            // THEN
            verify(savingAccountRepository, times(0)).deleteByClientByClientId(null);
            assertTrue("Client does not exist".equals(e.getMessage()));
        }

    }

    @Test
    public void removeByClientId_WhenAccountNull_ExpectedFalse() throws ApplicationException {

        // GIVEN
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setFirstname("Harry");
        clientEntity.setLastname("Boo");
        clientEntity.setPhone("0908070605");
        clientEntity.setEmail("hariboo@gmail.com");

        SpendingAccountEntity spendingAccountEntity = new SpendingAccountEntity();
        spendingAccountEntity.setSpendingId(1);
        spendingAccountEntity.setSpendingName("spending");
        spendingAccountEntity.setSpendingAmount(BigDecimal.valueOf(200).movePointLeft(2));
        spendingAccountEntity.setSpendingCredit(BigDecimal.valueOf(200).movePointLeft(2));

        when(clientRepository.findById(any(int.class))).thenReturn(Optional.of(clientEntity));
        when(spendingAccountRepository.findByClientByClientId(clientEntity)).thenReturn(spendingAccountEntity);
        when(savingAccountRepository.findByClientByClientId(clientEntity)).thenReturn(null);

        try {
            // WHEN
            boolean realResponse = savingAccountService.removeByClientId(1);
        }
        catch (ApplicationException e) {

            // THEN
            verify(savingAccountRepository, times(0)).deleteByClientByClientId(clientEntity);
            assertTrue("Saving Accounts do not exist".equals(e.getMessage()));
        }

    }

    @Test
    public void removeByClientId_WhenAccountExists_ExpectedTrue() throws ApplicationException {

        // GIVEN
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setFirstname("Harry");
        clientEntity.setLastname("Boo");
        clientEntity.setPhone("0908070605");
        clientEntity.setEmail("hariboo@gmail.com");

        SpendingAccountEntity spendingAccountEntity = new SpendingAccountEntity();
        spendingAccountEntity.setSpendingId(1);
        spendingAccountEntity.setSpendingName("spending");
        spendingAccountEntity.setSpendingAmount(BigDecimal.valueOf(200).movePointLeft(2));
        spendingAccountEntity.setSpendingCredit(BigDecimal.valueOf(200).movePointLeft(2));

        List<SavingAccountEntity> savingAccountEntities = new ArrayList<>();

        SavingAccountEntity saving1 = new SavingAccountEntity();
        saving1.setSavingId(1);
        saving1.setSavingName("Saving");
        saving1.setSavingAmount(BigDecimal.valueOf(20000).movePointLeft(2));
        saving1.setSavingMinimum(BigDecimal.valueOf(20).movePointLeft(2));
        saving1.setSavingInterest(BigDecimal.valueOf(1.2).movePointLeft(2));
        saving1.setClientByClientId(clientEntity);

        savingAccountEntities.add(saving1);

        SavingAccountEntity saving2 = new SavingAccountEntity();
        saving2.setSavingId(2);
        saving2.setSavingName("Money saved");
        saving2.setSavingAmount(BigDecimal.valueOf(10000).movePointLeft(2));
        saving2.setSavingMinimum(BigDecimal.valueOf(40).movePointLeft(2));
        saving2.setSavingInterest(BigDecimal.valueOf(2.2).movePointLeft(2));
        saving2.setClientByClientId(clientEntity);

        savingAccountEntities.add(saving2);

        when(clientRepository.findById(any(int.class))).thenReturn(Optional.of(clientEntity));
        when(spendingAccountRepository.findByClientByClientId(clientEntity)).thenReturn(spendingAccountEntity);
        when(savingAccountRepository.findByClientByClientId(clientEntity)).thenReturn(savingAccountEntities);
        when(spendingAccountRepository.save(spendingAccountEntity)).thenReturn(spendingAccountEntity);

        // WHEN
        boolean realResponse = savingAccountService.removeByClientId(1);
        verify(savingAccountRepository, times(1)).deleteByClientByClientId(clientEntity);

        // THEN
        assertTrue(realResponse);

    }

    @Test
    public void updateSavingAccount_WhenAccountNull_ExpectedNull() throws ApplicationException {

        // GIVEN
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setId(1);
        savingAccount.setName("saving");
        savingAccount.setAmount(BigDecimal.valueOf(2000).movePointLeft(2));
        savingAccount.setMinimum(BigDecimal.valueOf(20).movePointLeft(2));
        savingAccount.setInterest(BigDecimal.valueOf(2.0).movePointLeft(2));
        savingAccount.setClientId(1);

        when(savingAccountRepository.findById(any(int.class))).thenReturn(Optional.empty());

        try {
            // WHEN
            SavingAccount realResponse = savingAccountService.updateSavingAccount(1, savingAccount);
        }
        catch  (ApplicationException e) {

            // THEN
            assertTrue("Saving Account does not exist".equals(e.getMessage()));
        }
    }

    @Test
    public void updateSavingAccount_WhenAccountExists_ExpectedAccount() throws ApplicationException {

        // GIVEN
        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1);
        clientEntity.setFirstname("Harry");
        clientEntity.setLastname("Boo");
        clientEntity.setPhone("0908070605");
        clientEntity.setEmail("hariboo@gmail.com");

        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setName("saving");
        savingAccount.setAmount(BigDecimal.valueOf(2000).movePointLeft(2));
        savingAccount.setMinimum(BigDecimal.valueOf(20).movePointLeft(2));
        savingAccount.setInterest(BigDecimal.valueOf(2.0).movePointLeft(2));
        savingAccount.setClientId(1);

        SavingAccountEntity savingAccountEntity = new SavingAccountEntity();
        savingAccountEntity.setSavingName("saving");
        savingAccountEntity.setSavingAmount(BigDecimal.valueOf(2000).movePointLeft(2));
        savingAccountEntity.setSavingMinimum(BigDecimal.valueOf(20).movePointLeft(2));
        savingAccountEntity.setSavingInterest(BigDecimal.valueOf(2.0).movePointLeft(2));
        savingAccountEntity.setClientByClientId(clientEntity);

        when(savingAccountRepository.findById(any(int.class))).thenReturn(Optional.of(savingAccountEntity));
        when(savingAccountRepository.save(savingAccountEntity)).thenReturn(savingAccountEntity);

        // WHEN
        SavingAccount realResponse = savingAccountService.updateSavingAccount(1, savingAccount);

        // THEN
        assertTrue(realResponse != null && realResponse.getClientId() == 1);
    }
}