package com.bankwithfargo.controllers;

import com.bankwithfargo.dto.AccountRequestDTO;
import com.bankwithfargo.dto.CheckBalanceDTO;
import com.bankwithfargo.dto.DepositMoneyDTO;
import com.bankwithfargo.model.User;
import com.bankwithfargo.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/openAccount", method = POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> openAccount(@Valid @RequestBody AccountRequestDTO accountRequestDTO,
                                              @AuthenticationPrincipal User user) {
        return new ResponseEntity<Object>(accountService.openAccount(accountRequestDTO, user), HttpStatus.CREATED);
    }

    @GetMapping("/getTransactions/{accNo}")
    public ResponseEntity<Object> getTransactions(@PathVariable Long accNo){
        return new ResponseEntity<Object>(accountService.getTransactions(accNo),HttpStatus.OK);
    }

    @PostMapping("/checkBalance")
    public ResponseEntity<Object> checkBalance(@RequestBody CheckBalanceDTO checkBalanceDTO){
        return new ResponseEntity<Object>(accountService.checkBalance(checkBalanceDTO),HttpStatus.OK);
    }

    @RequestMapping(value="/getAccountsByUser", method=POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> getAccountsByUser(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(accountService.getAllAccounts(user), HttpStatus.OK);
    }

    @RequestMapping(value="/depositMoney", method=POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> depositMoney(@RequestBody DepositMoneyDTO depositMoneyDTO){
        return new ResponseEntity<Object>(accountService.depositMoney(depositMoneyDTO),HttpStatus.OK);
    }

    @RequestMapping(value="/withdrawMoney", method=POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> withdrawMoney(@RequestBody DepositMoneyDTO depositMoneyDTO){
        return new ResponseEntity<Object>(accountService.withdrawMoney(depositMoneyDTO),HttpStatus.OK);
    }

}
