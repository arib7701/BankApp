package com.bankapp.controller;

import com.bankapp.exception.ApplicationException;
import com.bankapp.model.SavingAccount;
import com.bankapp.service.ISavingAccountService;
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
@RequestMapping("/api/v1/account/saving")
public class SavingAccountController {

    @Autowired
    private ISavingAccountService savingAccountService;


    @RequestMapping(value= "/list", method = RequestMethod.GET)
    @ApiOperation(value = "getSavingAccountList", notes = "Get all saving accounts")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Valid request"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    public ResponseEntity<List<SavingAccount>> getSavingAccountList() throws ApplicationException {
        System.out.println("into list saving acc");
        List<SavingAccount> savingAccountBody = savingAccountService.findAll();
        return new ResponseEntity(savingAccountBody, HttpStatus.OK);
    }

    @RequestMapping(value= "/list/client/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "getSavingAccountListByClientId", notes = "Get all savings account by client id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Valid request"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    public ResponseEntity<List<SavingAccount>> getSavingAccountListByClientId(@ApiParam(value = "clientId", required = true) @PathVariable("id") int id) throws ApplicationException {
        List<SavingAccount> savingAccounts = savingAccountService.findAllByClientId(id);
        return new ResponseEntity(savingAccounts, HttpStatus.OK);
    }

    @RequestMapping(value= "/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "getSavingAccountById", notes = "Get one saving account by its id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Valid request"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    public ResponseEntity<SavingAccount> getSavingAccountById(@ApiParam(value = "id", required = true) @PathVariable ("id") int id) throws ApplicationException {
        SavingAccount savingAccounts = savingAccountService.findById(id);
        return new ResponseEntity(savingAccounts, HttpStatus.OK);

    }

    @RequestMapping(value= "/save", method = RequestMethod.POST)
    @ApiOperation(value = "saveSavingAccount", notes = "Saving new saving account")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Valid request"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    public ResponseEntity<SavingAccount> saveSavingAccount(@ApiParam(value = "savingAccount", required = true) @RequestBody SavingAccount savingAccountBody) throws ApplicationException {
        SavingAccount savingAccount = savingAccountService.save(savingAccountBody);
        return new ResponseEntity(savingAccount, HttpStatus.OK);
    }

    @RequestMapping(value= "/remove/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "removeSavingAccount", notes = "Remove saving account by its id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Valid request"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    public ResponseEntity<String> removeSavingAccount(@ApiParam(value = "id", required = true) @PathVariable("id") int id) throws ApplicationException {
        boolean deleted = savingAccountService.removeById(id);
        return new ResponseEntity("Saving account removed successfully", HttpStatus.OK);

    }

    @RequestMapping(value= "/remove/client/{id}", method = RequestMethod.DELETE)
    @ApiOperation(value = "removeSavingAccountByClientId", notes = "Remove all savings account by client id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Valid request"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    public ResponseEntity<String> removeSavingAccountByClientId(@ApiParam(value = "clientId", required = true) @PathVariable("id") int id) throws ApplicationException {
        boolean deleted = savingAccountService.removeByClientId(id);
        return new ResponseEntity("Saving account removed successfully", HttpStatus.OK);

    }

    @RequestMapping(value= "/update/{id}", method = RequestMethod.POST)
    @ApiOperation(value = "updateSavingAccount", notes = "Update saving account")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Valid request"),
            @ApiResponse(code = 400, message = "Invalid request"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    public ResponseEntity<SavingAccount> updateSavingAccount(@ApiParam(value = "savingAccount", required = true) @PathVariable("id") int id, @RequestBody SavingAccount savingAccountBody) throws ApplicationException {
        SavingAccount savingUpdated = savingAccountService.updateSavingAccount(id, savingAccountBody);
        return new ResponseEntity(savingUpdated, HttpStatus.OK);

    }
}
