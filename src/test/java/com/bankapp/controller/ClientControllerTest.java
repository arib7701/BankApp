package com.bankapp.controller;

import com.bankapp.exception.ApplicationErrorHandler;
import com.bankapp.exception.ApplicationException;
import com.bankapp.model.Client;
import com.bankapp.service.impl.ClientServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;


@RunWith(MockitoJUnitRunner.class)
public class ClientControllerTest {

    @InjectMocks
    ClientController clientController;

    @Mock
    ClientServiceImpl clientService;

    @Test
    public void getClientList_WhenClientsNull_Excepted400() throws Exception {

        // GIVEN
        when(clientService.findAll()).thenThrow(new ApplicationException("Client does not exist"));

        // WHEN
        MvcResult realResponse = standaloneSetup(clientController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(get("/api/v1/client/list").accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }

    @Test
    public void getClientList_WhenClientsExist_Excepted200() throws Exception {

        // GIVEN
        List<Client> clients = new ArrayList<>();

        Client client1 = new Client();
        client1.setId(1);
        client1.setFirstname("Harry");
        client1.setLastname("Boo");
        client1.setPhone("0908070605");
        client1.setEmail("hariboo@gmail.com");

        Client client2 = new Client();
        client2.setId(2);
        client2.setFirstname("John");
        client2.setLastname("Doe");
        client2.setPhone("0977070605");
        client2.setEmail("johndoe@gmail.com");

        clients.add(client1);
        clients.add(client2);

        when(clientService.findAll()).thenReturn(clients);

        // WHEN
        MvcResult realResponse = standaloneSetup(clientController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(get("/api/v1/client/list").accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 200);
        assertEquals(realResponse.getResponse().getContentAsString().contains("2"), true);
        assertEquals(realResponse.getResponse().getContentAsString().contains("John"), true);
    }

    @Test
    public void getClientById_WhenClientNull_Expected400() throws Exception {

        // GIVEN
        when(clientService.findById(any(int.class))).thenThrow(new ApplicationException("Client does not exist"));

        // WHEN
        MvcResult realResponse = standaloneSetup(clientController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(get("/api/v1/client/1").accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }

    @Test
    public void getClientById_WhenClientExists_Expected200() throws Exception {

        // GIVEN
        Client client = new Client();
        client.setId(1);
        client.setFirstname("Harry");
        client.setLastname("Boo");
        client.setPhone("0908070605");
        client.setEmail("hariboo@gmail.com");

        when(clientService.findById(any(int.class))).thenReturn(client);

        // WHEN
        MvcResult realResponse = standaloneSetup(clientController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(get("/api/v1/client/1").accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 200);
        assertEquals(realResponse.getResponse().getContentType(), "application/json;charset=UTF-8");
        assertEquals(realResponse.getResponse().getContentAsString().contains("1"), true);
        assertEquals(realResponse.getResponse().getContentAsString().contains("Harry"), true);
    }

    @Test
    public void getClientByEmail_WhenClientNull_Expected400() throws Exception {

        // GIVEN
        when(clientService.findByEmail(any(String.class))).thenThrow(new ApplicationException("Client does not exist"));
        String email = "email";

        // WHEN
        MvcResult realResponse = standaloneSetup(clientController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(get("/api/v1/client/email/"+email).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }

    @Test
    public void getClientByEmail_WhenClientExists_Expected200() throws Exception {

        // GIVEN
        Client client = new Client();
        client.setId(1);
        client.setFirstname("Harry");
        client.setLastname("Boo");
        client.setPhone("0908070605");
        client.setEmail("hariboo@gmailcom");
       // client.setAccounts(null);

        when(clientService.findByEmail(any(String.class))).thenReturn(client);
        String email = "hariboo@gmailcom";

        // WHEN
        MvcResult realResponse = standaloneSetup(clientController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(get("/api/v1/client/email/"+email).accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 200);
        assertEquals(realResponse.getResponse().getContentType(), "application/json;charset=UTF-8");
        assertEquals(realResponse.getResponse().getContentAsString().contains("1"), true);
        assertEquals(realResponse.getResponse().getContentAsString().contains("Harry"), true);
    }

    @Test
    public void saveClient_WhenClient_Expected200() throws Exception{

        // GIVEN
        Client client = new Client();
        client.setId(1);
        client.setFirstname("Harry");
        client.setLastname("Boo");
        client.setPhone("0908070605");
        client.setEmail("hariboo@gmailcom");

        String clientJson = "{\"firstname\":\"Harry\",\"lastname\":\"Boo\",\"phone\":\"0908070605\",\"email\":\"hariboo@gmailcom\"}";

        when(clientService.save(any(Client.class))).thenReturn(client);

        // WHEN
        MvcResult realResponse = standaloneSetup(clientController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(post("/api/v1/client/save").contentType(MediaType.APPLICATION_JSON).content(clientJson))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 200);
        assertEquals(realResponse.getResponse().getContentAsString().contains("Harry"), true);
    }

    @Test
    public void removeClientById_WhenClientNull_Expected400() throws Exception {

        // GIVEN
        when(clientService.removeById(any(int.class))).thenThrow(new ApplicationException("Client does not exist"));

        // WHEN
        MvcResult realResponse = standaloneSetup(clientController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(delete("/api/v1/client/remove/1").accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }

    @Test
    public void removeClientById_WhenClientExists_Expected200() throws Exception {

        // GIVEN
        when(clientService.removeById(any(int.class))).thenReturn(true);

        // WHEN
        MvcResult realResponse = standaloneSetup(clientController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(delete("/api/v1/client/remove/1").accept(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 200);
    }

    @Test
    public void updateClient_WhenClientNull_Expected400() throws Exception {

        // WHEN
        MvcResult realResponse = standaloneSetup(clientController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(post("/api/v1/client/update/1").contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 400);
    }

    @Test
    public void updateClient_WhenClientExists_Expected200() throws Exception {

        // GIVEN
        Client client = new Client();
        client.setId(1);
        client.setFirstname("Harry");
        client.setLastname("Boo");
        client.setPhone("0908070605");
        client.setEmail("hariboo@gmailcom");
        client.setAccounts(null);

        String clientJson = "{\"id\":\"1\",\"firstname\":\"Harry\",\"lastname\":\"Boo\",\"phone\":\"0908070605\",\"email\":\"hariboo@gmailcom\"}";

        when(clientService.update(any(int.class), any(Client.class))).thenReturn(client);

        // WHEN
        MvcResult realResponse = standaloneSetup(clientController)
                .setControllerAdvice(new ApplicationErrorHandler())
                .build()
                .perform(post("/api/v1/client/update/1", client).contentType(MediaType.APPLICATION_JSON).content(clientJson))
                .andReturn();

        // THEN
        assertEquals(realResponse.getResponse().getStatus(), 200);
        assertEquals(realResponse.getResponse().getContentAsString().contains("Harry"), true);
    }
}