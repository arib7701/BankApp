package com.bankapp.controller;

import com.bankapp.exception.ApplicationErrorHandler;
import com.bankapp.exception.ApplicationException;
import com.bankapp.model.SavingAccount;
import com.bankapp.service.impl.SavingAccountServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@RunWith(MockitoJUnitRunner.class)
public class SavingAccountControllerTest {

    @InjectMocks
    private SavingAccountController savingAccountController;

    @Mock
    private SavingAccountServiceImpl savingAccountService;

    @Test
    public void getSavingAccountList_WhenAccountsNull_Expected400() throws Exception {

        // GIVEN
        when(savingAccountService.findAll()).thenThrow(new ApplicationException("Saving Accounts do not exist"));

        // WHEN
        MvcResult realResponse = standaloneSetup(savingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(get("/api/v1/account/saving/list").accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }

    @Test
    public void getSavingAccountList_WhenAccountsExist_Expected200() throws Exception {

        // GIVEN
        List<SavingAccount> savingAccounts = new ArrayList<>();

        SavingAccount saving1 = new SavingAccount();
        saving1.setId(1);
        saving1.setName("Saving");
        saving1.setAmount(BigDecimal.valueOf(20000).movePointLeft(2));
        saving1.setMinimum(BigDecimal.valueOf(20).movePointLeft(2));
        saving1.setInterest(BigDecimal.valueOf(1.2).movePointLeft(2));
        saving1.setClientId(1);

        savingAccounts.add(saving1);

        SavingAccount saving2 = new SavingAccount();
        saving2.setId(2);
        saving2.setName("Money saved");
        saving2.setAmount(BigDecimal.valueOf(10000).movePointLeft(2));
        saving2.setMinimum(BigDecimal.valueOf(40).movePointLeft(2));
        saving2.setInterest(BigDecimal.valueOf(2.2).movePointLeft(2));
        saving2.setClientId(1);

        savingAccounts.add(saving2);

        when(savingAccountService.findAll()).thenReturn(savingAccounts);

        // WHEN
        MvcResult realResponse = standaloneSetup(savingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(get("/api/v1/account/saving/list").accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 200);
        assertEquals(realResponse.getResponse().getContentAsString().contains("Money saved"), true);
    }

    @Test
    public void getSavingAccountListByClientId_WhenClientNull_Expected400() throws Exception {

        // GIVEN
        when(savingAccountService.findAllByClientId(any(int.class))).thenThrow(new ApplicationException("Client does not exist"));

        // WHEN
        MvcResult realResponse = standaloneSetup(savingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(get("/api/v1/account/saving/list/client/{id}", 0).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }

    @Test
    public void getSavingAccountListByClientId_WhenAccountNull_Expected400() throws Exception {

        // GIVEN
        when(savingAccountService.findAllByClientId(any(int.class))).thenThrow(new ApplicationException("Saving Accounts do not exist"));

        // WHEN
        MvcResult realResponse = standaloneSetup(savingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(get("/api/v1/account/saving/list/client/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }

    @Test
    public void getSavingAccountListByClientId_WhenAccountExists_Expected200() throws Exception {

        // GIVEN
        List<SavingAccount> savingAccounts = new ArrayList<>();

        SavingAccount saving1 = new SavingAccount();
        saving1.setId(1);
        saving1.setName("Saving");
        saving1.setAmount(BigDecimal.valueOf(20000).movePointLeft(2));
        saving1.setMinimum(BigDecimal.valueOf(20).movePointLeft(2));
        saving1.setInterest(BigDecimal.valueOf(1.2).movePointLeft(2));
        saving1.setClientId(1);

        savingAccounts.add(saving1);

        SavingAccount saving2 = new SavingAccount();
        saving2.setId(2);
        saving2.setName("Money saved");
        saving2.setAmount(BigDecimal.valueOf(10000).movePointLeft(2));
        saving2.setMinimum(BigDecimal.valueOf(40).movePointLeft(2));
        saving2.setInterest(BigDecimal.valueOf(2.2).movePointLeft(2));
        saving2.setClientId(1);

        savingAccounts.add(saving2);

        when(savingAccountService.findAllByClientId(any(int.class))).thenReturn(savingAccounts);

        // WHEN
        MvcResult realResponse = standaloneSetup(savingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(get("/api/v1/account/saving/list/client/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 200);
        assertEquals(realResponse.getResponse().getContentAsString().contains("Money saved"), true);
    }

    @Test
    public void getSavingAccountById_WhenAccountNull_Expected400() throws Exception {

        // GIVEN
        when(savingAccountService.findById(any(int.class))).thenThrow(new ApplicationException("Saving Account does not exist"));

        MvcResult realResponse = standaloneSetup(savingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(get("/api/v1/account/saving/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }

    @Test
    public void getSavingAccountById_WhenAccountExists_Expected200() throws Exception {

        // GIVEN
        SavingAccount saving = new SavingAccount();
        saving.setId(1);
        saving.setName("Saving");
        saving.setAmount(BigDecimal.valueOf(20000).movePointLeft(2));
        saving.setMinimum(BigDecimal.valueOf(20).movePointLeft(2));
        saving.setInterest(BigDecimal.valueOf(1.2).movePointLeft(2));
        saving.setClientId(1);

        when(savingAccountService.findById(any(int.class))).thenReturn(saving);

        MvcResult realResponse = standaloneSetup(savingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(get("/api/v1/account/saving/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 200);
        assertEquals(realResponse.getResponse().getContentAsString().contains("Saving"), true);
    }

    @Test
    public void saveSavingAccount_WhenClientNull_Expected400() throws Exception {

        // GIVEN
        MvcResult realResponse = standaloneSetup(savingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(post("/api/v1/account/saving/save", 1).contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }


    @Test
    public void saveSavingAccount_WhenWrongBalance_Expected400() throws Exception {

        // GIVEN
        when(savingAccountService.save(any(SavingAccount.class))).thenThrow(new ApplicationException("Error: Saving Account balance is less than minimum required"));

        String savingJson = "{\"id\":\"1\",\"name\":\"Saving\",\"minimum\":\"50\",\"interest\":\"2.1\",\"amount\":\"20\",\"clientId\":\"1\"}";

        MvcResult realResponse = standaloneSetup(savingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(post("/api/v1/account/saving/save", 1).contentType(MediaType.APPLICATION_JSON).content(savingJson))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }

    @Test
    public void saveSavingAccount_WhenBalanceOK_Expected200() throws Exception {

        // GIVEN
        SavingAccount saving = new SavingAccount();
        saving.setId(1);
        saving.setName("Saving");
        saving.setAmount(BigDecimal.valueOf(20000).movePointLeft(2));
        saving.setMinimum(BigDecimal.valueOf(20).movePointLeft(2));
        saving.setInterest(BigDecimal.valueOf(1.2).movePointLeft(2));
        saving.setClientId(1);

        when(savingAccountService.save(any(SavingAccount.class))).thenReturn(saving);

        String savingJson = "{\"id\":\"1\",\"name\":\"Saving\",\"minimum\":\"20\",\"interest\":\"1.2\",\"amount\":\"20000\",\"clientId\":\"1\"}";

        MvcResult realResponse = standaloneSetup(savingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(post("/api/v1/account/saving/save", 1).contentType(MediaType.APPLICATION_JSON).content(savingJson))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 200);
        assertEquals(realResponse.getResponse().getContentAsString().contains("Saving"), true);
    }

    @Test
    public void removeSavingAccount_WhenClientNull_Expected400() throws Exception {

        // GIVEN
        when(savingAccountService.removeById(any(int.class))).thenThrow(new ApplicationException("Client does not exist, can not close the account"));

        MvcResult realResponse = standaloneSetup(savingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(delete("/api/v1/account/saving/remove/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }

    @Test
    public void removeSavingAccount_WhenClientExistsAccountNull_Expected400() throws Exception {

        // GIVEN
        when(savingAccountService.removeById(any(int.class))).thenThrow(new ApplicationException("Saving Account does not exist"));

        MvcResult realResponse = standaloneSetup(savingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(delete("/api/v1/account/saving/remove/{id}", 0).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }

    @Test
    public void removeSavingAccount_WhenClientExistsAccountExistSpendingNull_Expected400() throws Exception {

        // GIVEN
        when(savingAccountService.removeById(any(int.class))).thenThrow(new ApplicationException("Spending Account does not exist, can not transfer the balance and close the account."));

        MvcResult realResponse = standaloneSetup(savingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(delete("/api/v1/account/saving/remove/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }

    @Test
    public void removeSavingAccount_WhenClientExistsAccountExistSpendingOK_Expected200() throws Exception {

        // GIVEN
        when(savingAccountService.removeById(any(int.class))).thenReturn(true);

        MvcResult realResponse = standaloneSetup(savingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(delete("/api/v1/account/saving/remove/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 200);
    }

    @Test
    public void removeSavingAccountByClientId_WhenClientNull_Expected400() throws Exception {

        // GIVEN
        when(savingAccountService.removeByClientId(any(int.class))).thenThrow(new ApplicationException("Client does not exist"));

        MvcResult realResponse = standaloneSetup(savingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(delete("/api/v1/account/saving/remove/client/{id}", 0).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }

    @Test
    public void removeSavingAccountByClientId_WhenAccountNull_Expected400() throws Exception {

        // GIVEN
        when(savingAccountService.removeByClientId(any(int.class))).thenThrow(new ApplicationException("Saving Accounts do not exist"));

        MvcResult realResponse = standaloneSetup(savingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(delete("/api/v1/account/saving/remove/client/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }

    @Test
    public void removeSavingAccountByClientId_WhenNegativeBalance_Expected400() throws Exception {

        // GIVEN
        when(savingAccountService.removeByClientId(any(int.class))).thenThrow(new ApplicationException("At least one Saving Account has a negative balance and can't be closed"));

        MvcResult realResponse = standaloneSetup(savingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(delete("/api/v1/account/saving/remove/client/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }


    @Test
    public void removeSavingAccountByClientId_WhenSpendingNull_Expected400() throws Exception {

        // GIVEN
        when(savingAccountService.removeByClientId(any(int.class))).thenThrow(new ApplicationException("Spending Account does not exist, can not transfer the balance and close the account."));

        MvcResult realResponse = standaloneSetup(savingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(delete("/api/v1/account/saving/remove/client/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }

    @Test
    public void removeSavingAccountByClientId_WhenAllOK_Expected200() throws Exception {

        // GIVEN
        when(savingAccountService.removeByClientId(any(int.class))).thenReturn(true);

        MvcResult realResponse = standaloneSetup(savingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(delete("/api/v1/account/saving/remove/client/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 200);
    }

    @Test
    public void updateSavingAccount_WhenAccountNull_Expected400() throws Exception {

        // GIVEN
        MvcResult realResponse = standaloneSetup(savingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(post("/api/v1/account/saving/update/{id}", 0).contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }

    @Test
    public void updateSavingAccount_WhenWrongBalance_Expected400() throws Exception {

        // GIVEN
        when(savingAccountService.updateSavingAccount(any(int.class), any(SavingAccount.class))).thenThrow(new ApplicationException("Error: Saving Account balance is less than minimum required"));

        String savingJson = "{\"id\":\"1\",\"name\":\"Saving\",\"minimum\":\"50\",\"interest\":\"1.2\",\"amount\":\"20\",\"clientId\":\"1\"}";

        MvcResult realResponse = standaloneSetup(savingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(post("/api/v1/account/saving/update/{id}", 1).contentType(MediaType.APPLICATION_JSON).content(savingJson))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }

    @Test
    public void updateSavingAccount_WhenAccountOK_Expected200() throws Exception {

        // GIVEN
        SavingAccount saving = new SavingAccount();
        saving.setId(1);
        saving.setName("Saving");
        saving.setAmount(BigDecimal.valueOf(20000).movePointLeft(2));
        saving.setMinimum(BigDecimal.valueOf(20).movePointLeft(2));
        saving.setInterest(BigDecimal.valueOf(1.2).movePointLeft(2));
        saving.setClientId(1);

        String savingJson = "{\"id\":\"1\",\"name\":\"Saving\",\"minimum\":\"20\",\"interest\":\"1.2\",\"amount\":\"20000\",\"clientId\":\"1\"}";

        when(savingAccountService.updateSavingAccount(any(int.class), any(SavingAccount.class))).thenReturn(saving);

        MvcResult realResponse = standaloneSetup(savingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(post("/api/v1/account/saving/update/1").contentType(MediaType.APPLICATION_JSON).content(savingJson))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 200);
        assertEquals(realResponse.getResponse().getContentAsString().contains("Saving"), true);
    }
}