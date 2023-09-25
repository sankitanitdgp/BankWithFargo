package com.bankwithfargo.controllers;

import com.bankwithfargo.dto.*;
import com.bankwithfargo.entity.Transaction;
import com.bankwithfargo.entity.User;
import com.bankwithfargo.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTransactions() {
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();
        User user = new User();
        Transaction t=new Transaction();
        List<Transaction> transactions=new ArrayList<>(List.of(t));
        ResponseEntity<Object> r=new ResponseEntity<>(transactions, HttpStatus.OK);
        when(accountService.getTransactions(transactionRequestDTO, user)).thenReturn(r);

        ResponseEntity<Object> response = accountController.getTransactions(transactionRequestDTO, user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(r,response.getBody());
    }

    @Test
    public void testCheckBalance() {
        CheckBalanceDTO checkBalanceDTO = new CheckBalanceDTO();
        when(accountService.checkBalance(checkBalanceDTO)).thenReturn(new ResponseEntity<>(HttpStatus.OK));

        ResponseEntity<Object> response = accountController.checkBalance(checkBalanceDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testDepositMoney() {
        DepositMoneyDTO depositMoneyDTO = new DepositMoneyDTO();
        when(accountService.depositMoney(depositMoneyDTO)).thenReturn(String.valueOf(new ResponseEntity<>(HttpStatus.OK)));

        ResponseEntity<Object> response = accountController.depositMoney(depositMoneyDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testWithdrawMoney() {
        DepositMoneyDTO depositMoneyDTO = new DepositMoneyDTO();
        when(accountService.withdrawMoney(depositMoneyDTO)).thenReturn(String.valueOf(new ResponseEntity<>(HttpStatus.OK)));

        ResponseEntity<Object> response = accountController.withdrawMoney(depositMoneyDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testTransferMoney() {
        TransferMoneyDTO transferMoneyDTO = new TransferMoneyDTO();
        when(accountService.transferMoney(transferMoneyDTO)).thenReturn(String.valueOf(new ResponseEntity<>(HttpStatus.OK)));

        ResponseEntity<Object> response = accountController.transferMoney(transferMoneyDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testChangeAccountStatus() {
        AccountNoDTO accountNoDTO = new AccountNoDTO();
        User user = new User();
        when(accountService.changeAccountStatus(accountNoDTO, user)).thenReturn(new ResponseEntity<>(HttpStatus.OK).hasBody());

        ResponseEntity<Object> response = accountController.changeAccountStatus(accountNoDTO, user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}

