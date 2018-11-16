package com.bankapp.controller;


import com.bankapp.exception.ApplicationException;
import com.bankapp.model.SpendingAccount;
import com.bankapp.service.ISpendingAccountService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/account/spending")
public class SpendingAccountController {

    @Autowired
    private ISpendingAccountService spendingAccountService;


    @RequestMapping(value= "/list", method = RequestMethod.GET)
    @ApiOperation(value = "getSpendingAccountList", notes = "Get all spending accounts")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Valid request"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    public ResponseEntity<SpendingAccount> getSpendingAccountList() throws ApplicationException {
        List<SpendingAccount> spendingAccounts = spendingAccountService.findAll();
        return new ResponseEntity(spendingAccounts, HttpStatus.OK);
    }

    @RequestMapping(value= "/list/client/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "getSpendingAccountByClientId", notes = "Get spending account by its client id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Valid request"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    public ResponseEntity<SpendingAccount> getSpendingAccountByClientId(@ApiParam(value = "clientId", required = true) @PathVariable("id") int id) throws ApplicationException {
        SpendingAccount spendingAccount = spendingAccountService.findByClientId(id);
        return new ResponseEntity(spendingAccount, HttpStatus.OK);
    }

    @RequestMapping(value= "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "getSpendingAccountById", notes = "Get spending account by its id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Valid request"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    public ResponseEntity<SpendingAccount> getSpendingAccountById(@ApiParam(value = "id", required = true) @PathVariable ("id") int id) throws ApplicationException {
        SpendingAccount spendingAccount = spendingAccountService.findById(id);
        return new ResponseEntity(spendingAccount, HttpStatus.OK);

    }

    @RequestMapping(value= "/save", method = RequestMethod.POST)
    @ApiOperation(value = "saveSpendingAccount", notes = "Save new spending account")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Valid request"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    public ResponseEntity<SpendingAccount> saveSpendingAccount(@ApiParam(value = "spendingAccount", required = true) @RequestBody SpendingAccount spendingAccountBody) throws ApplicationException {
       SpendingAccount spendingAccount = spendingAccountService.save(spendingAccountBody);
        return new ResponseEntity(spendingAccount, HttpStatus.OK);
    }

    @RequestMapping(value= "/remove/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "removeSpendingAccount", notes = "Remove saving account by its id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Valid request"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    public ResponseEntity<String> removeSpendingAccount(@ApiParam(value = "id", required = true) @PathVariable("id") int id) throws ApplicationException {
        boolean deleted = spendingAccountService.removeById(id);
        return new ResponseEntity("Spending accounts removed successfully", HttpStatus.OK);


    }

    @RequestMapping(value= "/remove/client/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "removeSpendingAccountByClientId", notes = "Remove spending account by client id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Valid request"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    public ResponseEntity<String> removeSpendingAccountByClientId(@ApiParam(value = "clientId", required = true) @PathVariable("id") int id) throws ApplicationException {
        boolean deleted = spendingAccountService.removeByClientId(id);
        return new ResponseEntity("Spending accounts removed successfully", HttpStatus.OK);

    }

    @RequestMapping(value= "/update/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "updateSpendingAccount", notes = "Update spending account")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Valid request"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    public ResponseEntity<SpendingAccount> updateSpendingAccount(@ApiParam(value = "spendingAccount", required = true) @PathVariable("id") int id, @RequestBody SpendingAccount spendingAccountBody) throws ApplicationException {
        SpendingAccount spendingUpdated = spendingAccountService.updateSpendingAccount(id, spendingAccountBody);
        return new ResponseEntity(spendingUpdated, HttpStatus.OK);

    }
}

