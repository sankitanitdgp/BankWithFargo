package com.bankwithfargo.controllers;

import com.bankwithfargo.dto.*;
import com.bankwithfargo.entity.Account;
import com.bankwithfargo.entity.Transaction;
import com.bankwithfargo.entity.User;
import com.bankwithfargo.exception.AccountNotFoundException;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
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
    public void testOpenAccount() {
        // Create a mock response from accountService.openAccount()
        AccountRequestDTO accountRequestDTO = new AccountRequestDTO();
        User user = new User();
        Account account = new Account();
        account.setAccountNumber(1234L);

        when(accountService.openAccount(accountRequestDTO, user)).thenReturn(account);

        // Perform the test
        ResponseEntity<Object> response = accountController.openAccount(accountRequestDTO, user);

        // Verify that accountService.openAccount() was called with the mock AccountRequestDTO and User
        verify(accountService, times(1)).openAccount(accountRequestDTO, user);

        // Verify the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(account, response.getBody());
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
    public void testGetTransactionsWithException() {
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO();
        User user = new User();
        // Configure accountService.getTransactions() to throw a NullPointerException
        when(accountService.getTransactions(transactionRequestDTO, user)).thenThrow(new NullPointerException("Account does not exist"));

        // Perform the test and expect an AccountNotFoundException
        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> {
            accountController.getTransactions(transactionRequestDTO, user);
        });

        // Verify the exception message
        assertEquals("Account does not exist", exception.getMessage());
    }

    @Test
    public void testCheckBalance() {
        CheckBalanceDTO checkBalanceDTO = new CheckBalanceDTO();

        ResponseEntity<Object> mockResponseEntity = new ResponseEntity<>(100.0, HttpStatus.OK);
        when(accountService.checkBalance(checkBalanceDTO)).thenReturn(mockResponseEntity);

        // Perform the test
        ResponseEntity<Object> response = accountController.checkBalance(checkBalanceDTO);

        // Verify that accountService.checkBalance() was called with the mock CheckBalanceDTO
        verify(accountService, times(1)).checkBalance(checkBalanceDTO);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(new ResponseEntity<>(100.0,HttpStatus.OK), response.getBody());
    }

    @Test
    public void testCheckBalanceWithException() {
        CheckBalanceDTO checkBalanceDTO = new CheckBalanceDTO();
        // Configure accountService.checkBalance() to throw a NullPointerException
        when(accountService.checkBalance(checkBalanceDTO)).thenThrow(new NullPointerException("Account does not exist"));

        // Perform the test and expect an AccountNotFoundException
        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> {
            accountController.checkBalance(checkBalanceDTO);
        });

        // Verify the exception message
        assertEquals("Account does not exist", exception.getMessage());
    }

    @Test
    public void testGetAccountsByUser() {
        User user = new User();
        List<Account> mockAccounts = new ArrayList<>();
        when(accountService.getAllAccountsByUser(user)).thenReturn(mockAccounts);

        // Perform the test
        ResponseEntity<Object> response = accountController.getAccountsByUser(user);

        // Verify that accountService.getAllAccountsByUser() was called with the mock User object
        verify(accountService, times(1)).getAllAccountsByUser(user);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockAccounts, response.getBody());
    }

    @Test
    public void testDepositMoney() {
        DepositMoneyDTO depositMoneyDTO = new DepositMoneyDTO();

        when(accountService.depositMoney(depositMoneyDTO)).thenReturn("Money deposited successfully!");

        // Perform the test
        ResponseEntity<Object> response = accountController.depositMoney(depositMoneyDTO);

        // Verify that accountService.depositMoney() was called with the mock DepositMoneyDTO
        verify(accountService, times(1)).depositMoney(depositMoneyDTO);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Money deposited successfully!", response.getBody());
    }

    @Test
    public void testDepositMoneyWithException() {
        DepositMoneyDTO depositMoneyDTO = new DepositMoneyDTO();

        when(accountService.depositMoney(depositMoneyDTO)).thenThrow(new NullPointerException("Account does not exist"));

        // Perform the test and expect an AccountNotFoundException
        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> {
            accountController.depositMoney(depositMoneyDTO);
        });

        // Verify the exception message
        assertEquals("Account does not exist", exception.getMessage());
    }

    @Test
    public void testWithdrawMoney() {
        DepositMoneyDTO depositMoneyDTO = new DepositMoneyDTO();
        String res = "Money withdrawn successfully!";

        when(accountService.withdrawMoney(depositMoneyDTO)).thenReturn(res);

        // Perform the test
        ResponseEntity<Object> response = accountController.withdrawMoney(depositMoneyDTO);

        // Verify that accountService.withdrawMoney() was called with the mock DepositMoneyDTO
        verify(accountService, times(1)).withdrawMoney(depositMoneyDTO);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Money withdrawn successfully!", response.getBody());
    }

    @Test
    public void testWithdrawMoneyWithException() {
        DepositMoneyDTO depositMoneyDTO = new DepositMoneyDTO();
        when(accountService.withdrawMoney(depositMoneyDTO)).thenThrow(new NullPointerException("Account does not exist"));

        // Perform the test and expect an AccountNotFoundException
        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> {
            accountController.withdrawMoney(depositMoneyDTO);
        });

        // Verify the exception message
        assertEquals("Account does not exist", exception.getMessage());
    }

    @Test
    public void testTransferMoney() {
        TransferMoneyDTO transferMoneyDTO = new TransferMoneyDTO();
        String res = "Money transferred successfully!";

        when(accountService.transferMoney(transferMoneyDTO)).thenReturn(res);

        // Perform the test
        ResponseEntity<Object> response = accountController.transferMoney(transferMoneyDTO);

        // Verify that accountService.withdrawMoney() was called with the mock DepositMoneyDTO
        verify(accountService, times(1)).transferMoney(transferMoneyDTO);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Money transferred successfully!", response.getBody());

    }

    @Test
    public void testTransferMoneyWithException() {
        TransferMoneyDTO transferMoneyDTO = new TransferMoneyDTO();
        when(accountService.transferMoney(transferMoneyDTO)).thenThrow(new NullPointerException("Sender account is invalid"));

        // Perform the test and expect an AccountNotFoundException
        AccountNotFoundException exception = assertThrows(AccountNotFoundException.class, () -> {
            accountController.transferMoney(transferMoneyDTO);
        });

        // Verify the exception message
        assertEquals("Sender account is invalid", exception.getMessage());
    }


    @Test
    public void testChangeAccountStatus() {
        AccountNoDTO accountNoDTO = new AccountNoDTO();
        User user = new User();
        ResponseEntity<Object> mockResponseEntity = new ResponseEntity<>(true, HttpStatus.OK);

        // Configure accountService.changeAccountStatus() to return the mock response
        when(accountService.changeAccountStatus(accountNoDTO, user)).thenReturn(true);

        // Perform the test
        ResponseEntity<Object> response = accountController.changeAccountStatus(accountNoDTO, user);

        // Verify that accountService.changeAccountStatus() was called with the mock AccountNoDTO and User
        verify(accountService, times(1)).changeAccountStatus(accountNoDTO, user);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }
}

