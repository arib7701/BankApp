package com.bankapp.controller;

import com.bankapp.exception.ApplicationException;
import com.bankapp.model.Client;
import com.bankapp.service.IClientService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "https://progressivebankapp.herokuapp.com")
@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    @Autowired
    private IClientService clientService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "getClientList", notes = "Get all clients")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Valid request"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    public ResponseEntity<Client> getClientList() throws ApplicationException {
        List<Client> clients =  clientService.findAll();
        return new ResponseEntity(clients, HttpStatus.OK);
    }

    @RequestMapping(value= "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "getClientById", notes = "Get one client by its id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Valid request"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    public ResponseEntity<Client> getClientById(@ApiParam(value = "id", required = true) @PathVariable("id") int id) throws ApplicationException {
        Client client = clientService.findById(id);
        return new ResponseEntity(client, HttpStatus.OK);
    }

    @RequestMapping(value= "/email/{email}", method = RequestMethod.GET)
    @ApiOperation(value = "getClientByEmail", notes = "Get one client by its email")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Valid request"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    public ResponseEntity<Client> getClientByEmail(@ApiParam(value = "email", required = true) @PathVariable("email") String email) throws ApplicationException {
        Client client = clientService.findByEmail(email);
        return new ResponseEntity(client, HttpStatus.OK);
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    @ApiOperation(value = "saveClient", notes = "Create a new Client")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Valid request"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    public ResponseEntity<Client> saveClient(@ApiParam(value = "client", required = false) @RequestBody Client clientBody) throws ApplicationException {
        Client client = clientService.save(clientBody);
        return new ResponseEntity(client, HttpStatus.OK);

    }

    @RequestMapping(value="/remove/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "removeClientById", notes = "Remove client by its id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Valid request"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    public ResponseEntity<String> removeClientById(@ApiParam(value = "id", required = true) @PathVariable("id") int id) throws ApplicationException {
        boolean deleted = clientService.removeById(id);
        return new ResponseEntity("Client removed successfully", HttpStatus.OK);

    }

    @RequestMapping(value="/update/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "updateClient", notes = "Update client")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Valid request"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    public ResponseEntity<Client> updateClient(@ApiParam(value = "client", required = true) @PathVariable("id") int id, @RequestBody Client clientBody) throws ApplicationException{
        Client client = clientService.update(id, clientBody);
        return new ResponseEntity(client, HttpStatus.OK);

    }


}
