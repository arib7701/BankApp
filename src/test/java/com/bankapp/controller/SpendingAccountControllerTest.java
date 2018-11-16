package com.bankapp.controller;

import com.bankapp.exception.ApplicationErrorHandler;
import com.bankapp.exception.ApplicationException;
import com.bankapp.model.SpendingAccount;
import com.bankapp.service.impl.SpendingAccountServiceImpl;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(MockitoJUnitRunner.class)
public class SpendingAccountControllerTest {

    @InjectMocks
    SpendingAccountController spendingAccountController;

    @Mock
    SpendingAccountServiceImpl spendingAccountService;

    @Test
    public void getSpendingAccountList_WhenAccountNull_Expected400() throws  Exception {

        // GIVEN
        when(spendingAccountService.findAll()).thenThrow(new ApplicationException("Spending accounts do not exist !"));

        // WHEN
        MvcResult realResponse = standaloneSetup(spendingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(get("/api/v1/account/spending/list").accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }

    @Test
    public void getSpendingAccountList_WhenAccountExist_Expected200() throws Exception {

        // GIVEN
        List<SpendingAccount> spendingAccountList = new ArrayList<>();

        SpendingAccount spendingAccount1 = new SpendingAccount();
        spendingAccount1.setId(1);
        spendingAccount1.setName("spending");
        spendingAccount1.setCredit(BigDecimal.valueOf(200));
        spendingAccount1.setAmount(BigDecimal.valueOf(2000));
        spendingAccount1.setClientId(1);

        SpendingAccount spendingAccount2 = new SpendingAccount();
        spendingAccount2.setId(2);
        spendingAccount2.setName("income");
        spendingAccount2.setCredit(BigDecimal.valueOf(100));
        spendingAccount2.setAmount(BigDecimal.valueOf(3000));
        spendingAccount2.setClientId(1);

        spendingAccountList.add(spendingAccount1);
        spendingAccountList.add(spendingAccount2);

        when(spendingAccountService.findAll()).thenReturn(spendingAccountList);

        // WHEN
        MvcResult realResponse = standaloneSetup(spendingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(get("/api/v1/account/spending/list").accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 200);
        assertEquals(realResponse.getResponse().getContentAsString().contains("1"), true);
        assertEquals(realResponse.getResponse().getContentAsString().contains("income"), true);
    }

    @Test
    public void getSpendingAccountByClientId_WhenClientNull_Expected400() throws  Exception {

        // GIVEN
        when(spendingAccountService.findByClientId(0)).thenThrow(new ApplicationException("Client does not exist !"));

        // WHEN
        MvcResult realResponse = standaloneSetup(spendingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(get("/api/v1/account/spending/list/client/{id}", 0).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }

    @Test
    public void getSpendingAccountByClientId_WhenClientExistAccountNull_Expected400() throws  Exception {

        // GIVEN
        when(spendingAccountService.findByClientId(0)).thenThrow(new ApplicationException("Spending account does not exist !"));

        // WHEN
        MvcResult realResponse = standaloneSetup(spendingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(get("/api/v1/account/spending/list/client/{id}", 0).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }

    @Test
    public void getSpendingAccountByClientId_WhenClientExistAccountExist_Expected200() throws  Exception {

        // GIVEN
        SpendingAccount spendingAccount = new SpendingAccount();
        spendingAccount.setId(1);
        spendingAccount.setName("income");
        spendingAccount.setCredit(BigDecimal.valueOf(100));
        spendingAccount.setAmount(BigDecimal.valueOf(3000));
        spendingAccount.setClientId(1);

        when(spendingAccountService.findByClientId(any(int.class))).thenReturn(spendingAccount);

        // WHEN
        MvcResult realResponse = standaloneSetup(spendingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(get("/api/v1/account/spending/list/client/{id}", 1).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 200);
        assertEquals(realResponse.getResponse().getContentAsString().contains("income"), true);
    }

    @Test
    public void getSpendingAccountById_WhenAccountNull_Expected400() throws  Exception {

        // GIVEN
        when(spendingAccountService.findById(0)).thenThrow(new ApplicationException("Spending account does not exist !"));

        // WHEN
        MvcResult realResponse = standaloneSetup(spendingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(get("/api/v1/account/spending/{id}", 0).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }


    @Test
    public void getSpendingAccountById_WhenAccountExists_Expected200() throws  Exception {

        // GIVEN
        SpendingAccount spendingAccount = new SpendingAccount();
        spendingAccount.setId(1);
        spendingAccount.setName("income");
        spendingAccount.setCredit(BigDecimal.valueOf(100));
        spendingAccount.setAmount(BigDecimal.valueOf(3000));
        spendingAccount.setClientId(1);

        when(spendingAccountService.findById(any(int.class))).thenReturn(spendingAccount);

        // WHEN
        MvcResult realResponse = standaloneSetup(spendingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(get("/api/v1/account/spending/{id}", 0).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 200);
        assertEquals(realResponse.getResponse().getContentAsString().contains("income"), true);
    }


    @Test
    public void saveSpendingAccount_WhenClientNull_Expected400() throws Exception {

        // WHEN
        MvcResult realResponse = standaloneSetup(spendingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(post("/api/v1/account/spending/save").contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }

    @Test
    public void saveSpendingAccount_WhenClientExists_Expected200() throws Exception {

        // GIVEN
        SpendingAccount spendingAccount = new SpendingAccount();
        spendingAccount.setId(1);
        spendingAccount.setName("income");
        spendingAccount.setCredit(BigDecimal.valueOf(100));
        spendingAccount.setAmount(BigDecimal.valueOf(3000));
        spendingAccount.setClientId(1);

        String spendingJson = "{\"id\":\"1\",\"name\":\"income\",\"credit\":\"100\",\"amount\":\"3000\",\"clientId\":\"0\"}";

        when(spendingAccountService.save(any(SpendingAccount.class))).thenReturn(spendingAccount);

        // WHEN
        MvcResult realResponse = standaloneSetup(spendingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(post("/api/v1/account/spending/save").contentType(MediaType.APPLICATION_JSON).content(spendingJson))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 200);
        assertEquals(realResponse.getResponse().getContentAsString().contains("1"), true);
        assertEquals(realResponse.getResponse().getContentAsString().contains("income"), true);
    }

    @Test
    public void removeSpendingAccount_WhenClientNull_Expected400() throws Exception {

        // GIVEN
        when(spendingAccountService.removeById(any(int.class))).thenThrow(new ApplicationException("Client does not exist"));

        // WHEN
        MvcResult realResponse = standaloneSetup(spendingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(delete("/api/v1/account/spending/remove/1").accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }

    @Test
    public void removeSpendingAccount_WhenClientExistsAccountNull_Expected400() throws Exception {

        // GIVEN
        when(spendingAccountService.removeById(any(int.class))).thenThrow(new ApplicationException("Spending account does not exist !"));

        // WHEN
        MvcResult realResponse = standaloneSetup(spendingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(delete("/api/v1/account/spending/remove/1").accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }

    @Test
    public void removeSpendingAccount_WhenClientExistsAccountExists_Expected200() throws Exception {

        // GIVEN
        when(spendingAccountService.removeById(any(int.class))).thenReturn(true);

        // WHEN
        MvcResult realResponse = standaloneSetup(spendingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(delete("/api/v1/account/spending/remove/1").accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 200);
    }


    @Test
    public void removeSpendingAccountByClientId_WhenClientNull_Expected400() throws Exception {

        // GIVEN
        when(spendingAccountService.removeByClientId(any(int.class))).thenThrow(new ApplicationException("Client does not exist !"));

        // WHEN
        MvcResult realResponse = standaloneSetup(spendingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(delete("/api/v1/account/spending/remove/client/1").accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }


    @Test
    public void removeSpendingAccountByClientId_WhenClientExists_Expected200() throws Exception {

        // GIVEN
        when(spendingAccountService.removeByClientId(any(int.class))).thenReturn(true);

        // WHEN
        MvcResult realResponse = standaloneSetup(spendingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(delete("/api/v1/account/spending/remove/client/1").accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 200);
    }

    @Test
    public void updateSpendingAccount_WhenAccountNull_Expected400() throws Exception {

        // WHEN
        MvcResult realResponse = standaloneSetup(spendingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(post("/api/v1/account/spending/update/1").contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }

    @Test
    public void updateSpendingAccount_WhenAccountExists_Expected200() throws Exception {

        // GIVEN
        SpendingAccount spendingAccount = new SpendingAccount();
        spendingAccount.setId(1);
        spendingAccount.setName("income");
        spendingAccount.setCredit(BigDecimal.valueOf(100));
        spendingAccount.setAmount(BigDecimal.valueOf(3000));
        spendingAccount.setClientId(1);

        String spendingJson = "{\"id\":\"1\",\"name\":\"income\",\"credit\":\"100\",\"amount\":\"3000\",\"clientId\":\"0\"}";

        when(spendingAccountService.updateSpendingAccount(any(int.class), any(SpendingAccount.class))).thenReturn(spendingAccount);

        // WHEN
        MvcResult realResponse = standaloneSetup(spendingAccountController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(post("/api/v1/account/spending/update/1").contentType(MediaType.APPLICATION_JSON).content(spendingJson))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 200);
        assertEquals(realResponse.getResponse().getContentAsString().contains("1"), true);
        assertEquals(realResponse.getResponse().getContentAsString().contains("income"), true);
    }
}