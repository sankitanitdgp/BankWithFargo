package com.bankwithfargo.controllers;

import com.bankwithfargo.dto.*;
import com.bankwithfargo.entity.User;
import com.bankwithfargo.exception.AccountNotFoundException;
import com.bankwithfargo.exception.InvalidArgumentsException;
import com.bankwithfargo.service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

        try {
            return new ResponseEntity<Object>(accountService.openAccount(accountRequestDTO, user), HttpStatus.CREATED);
        } catch(NullPointerException e) {
            throw new InvalidArgumentsException("Field values cannot be null");
        } catch (InvalidArgumentsException e){
            throw new InvalidArgumentsException("Invalid method arguments");
        }
    }

    @PostMapping("/getTransactions")
    public ResponseEntity<Object> getTransactions(@Valid @RequestBody TransactionRequestDTO accNo,
                                                  @AuthenticationPrincipal User user){
        try {
            return new ResponseEntity<Object>(accountService.getTransactions(accNo, user), HttpStatus.OK);
        } catch(NullPointerException e){
            throw new AccountNotFoundException("Account does not exists");
        }
    }

    @PostMapping("/checkBalance")
    public ResponseEntity<Object> checkBalance(@RequestBody CheckBalanceDTO checkBalanceDTO){
        try {
            return new ResponseEntity<Object>(accountService.checkBalance(checkBalanceDTO), HttpStatus.OK);
        } catch(NullPointerException e){
            throw new AccountNotFoundException("Account does not exists");
        }
    }

    @RequestMapping(value="/getAccountsByUser", method=POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> getAccountsByUser(@AuthenticationPrincipal User user) {
        return new ResponseEntity<>(accountService.getAllAccountsByUser(user), HttpStatus.OK);
    }

    @RequestMapping(value="/depositMoney", method=POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> depositMoney(@RequestBody DepositMoneyDTO depositMoneyDTO){
        try {
            return new ResponseEntity<Object>(accountService.depositMoney(depositMoneyDTO), HttpStatus.OK);
        } catch(NullPointerException e){
            throw new AccountNotFoundException("Account does not exists");
        }
    }

    @RequestMapping(value="/withdrawMoney", method=POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> withdrawMoney(@RequestBody DepositMoneyDTO depositMoneyDTO){
        try {
            return new ResponseEntity<Object>(accountService.withdrawMoney(depositMoneyDTO), HttpStatus.OK);
        } catch(NullPointerException e){
            throw new AccountNotFoundException("Account does not exists");
        }
    }

    @RequestMapping(value="/transferMoney", method=POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> transferMoney(@Valid @RequestBody TransferMoneyDTO transferMoneyDTO){
        try {
            return new ResponseEntity<Object>(accountService.transferMoney(transferMoneyDTO), HttpStatus.OK);
        } catch(NullPointerException e){
            throw new AccountNotFoundException("Sender account is invalid");
        } catch (InvalidArgumentsException e){
            throw new InvalidArgumentsException("Invalid method arguments");
        }
    }

    @RequestMapping(value="/changeAccountStatus", method=POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> changeAccountStatus(@RequestBody AccountNoDTO accountNoDTO,
                                                      @AuthenticationPrincipal User user){
        return new ResponseEntity<Object>(accountService.changeAccountStatus(accountNoDTO,user),HttpStatus.OK);
    }

}
