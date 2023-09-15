package com.bankwithfargo.controllers;

import com.bankwithfargo.dto.AccountRequestDTO;
import com.bankwithfargo.dto.CheckBalanceDTO;
import com.bankwithfargo.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/openAccount", method = POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> openAccount(@Valid @RequestBody AccountRequestDTO accountRequestDTO) {
        return new ResponseEntity<Object>(accountService.openAccount(accountRequestDTO), HttpStatus.CREATED);
    }

    @GetMapping("/getTransactions/{accNo}")
    public ResponseEntity<Object> getTransactions(@PathVariable Long accNo){
        return new ResponseEntity<Object>(accountService.getTransactions(accNo),HttpStatus.OK);
    }

    @PostMapping("/checkBalance")
    public ResponseEntity<Object> checkBalance(@RequestBody CheckBalanceDTO checkBalanceDTO){
        return new ResponseEntity<Object>(accountService.checkBalance(checkBalanceDTO),HttpStatus.OK);
    }
}
