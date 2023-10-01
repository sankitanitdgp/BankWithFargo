package com.bankwithfargo.service;

import com.bankwithfargo.dto.*;
import com.bankwithfargo.entity.Account;
import com.bankwithfargo.entity.Transaction;
import com.bankwithfargo.entity.User;
import com.bankwithfargo.repository.AccountRepository;
import com.bankwithfargo.repository.TransactionRepository;
import com.bankwithfargo.repository.UserLoginRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserLoginRepository userLoginRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testOpenAccount() {

        AccountRequestDTO accountRequestDTO = new AccountRequestDTO();
        accountRequestDTO.setTitle("Mr.");
        accountRequestDTO.setFirstName("John");
        accountRequestDTO.setLastName("Doe");
        accountRequestDTO.setDob(new Date(2023,9,20));
        accountRequestDTO.setFatherName("Doe");
        accountRequestDTO.setPhone("1234567890");
        accountRequestDTO.setPresentAddress("D");
        accountRequestDTO.setPermanentAddress("John");
        accountRequestDTO.setPan("qwert4567y");
        accountRequestDTO.setOccupationType("qwe");
        accountRequestDTO.setIncome(12345L);
        accountRequestDTO.setEmail("john@example.com");
        accountRequestDTO.setMpin(1234);

        User user = new User();
        user.setEmail("john@example.com");

        Account newAccount = new Account();
        newAccount.setAccountNumber(12345L);
        newAccount.setAccountStatus((true));
        newAccount.setDateOfOpening(new Date(2023,9,20));
        newAccount.setIfsc("IFSC0099123");
        newAccount.setBalance(0.0);
        newAccount.setUser(user);
        BeanUtils.copyProperties(accountRequestDTO, newAccount);

        when(accountRepository.save(any(Account.class))).thenReturn(newAccount);

        // Act
        Account openedAccount = accountService.openAccount(accountRequestDTO, user);

        // Assert
        assertNotNull(openedAccount);
        assertTrue(openedAccount.getAccountStatus());
        assertEquals("IFSC0099123", openedAccount.getIfsc());
        assertEquals(0.0, openedAccount.getBalance());
        assertEquals(new Date(2023,9,20), openedAccount.getDateOfOpening());
        assertEquals(user.getEmail(), openedAccount.getEmail());
        assertEquals(user, openedAccount.getUser());
    }

    @Test
    public void testGetTransactions() {
        TransactionRequestDTO dto=new TransactionRequestDTO();
        dto.setAccNo(12345L);
        dto.setMpin(1234);
        Account account=new Account();
        account.setMpin(1234);
        Transaction t=new Transaction();
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(t);
        when(transactionRepository.findAllBySenderAccNoOrReceiverAccNo(dto.getAccNo(), dto.getAccNo())).thenReturn(transactions);
        when(accountRepository.findByAccountNumber(dto.getAccNo())).thenReturn(account);

        User user = new User();
        user.setEmail("jane@gmail.com");
        user.setPassword("password");
        ResponseEntity<Object> result = accountService.getTransactions(dto,user);

        // Assert
        assertNotNull(result);
        assertEquals(ResponseEntity.ok(transactions), result);
    }

    @Test
    public void testCheckBalanceAccountExistsCorrectMPIN() {
        // Arrange
        CheckBalanceDTO checkBalanceDTO = new CheckBalanceDTO();
        checkBalanceDTO.setAccNo(12345L);
        checkBalanceDTO.setMpin(1234);

        Account account = new Account();
        account.setAccountNumber(checkBalanceDTO.getAccNo());
        account.setMpin(checkBalanceDTO.getMpin());
        account.setBalance(1000.0);

        when(accountRepository.findByAccountNumber(checkBalanceDTO.getAccNo())).thenReturn(account);

        Object result = accountService.checkBalance(checkBalanceDTO);

        assertEquals(account.getBalance(), result);
    }

    @Test
    public void testCheckBalanceAccountDoesNotExist() {
        // Arrange
        CheckBalanceDTO checkBalanceDTO = new CheckBalanceDTO();
        checkBalanceDTO.setAccNo(12345L);
        checkBalanceDTO.setMpin(1234);

        Account account=new Account();
        account.setMpin(1234);
        account.setBalance(10D);
        account.setAccountNumber(checkBalanceDTO.getAccNo());
        when(accountRepository.findByAccountNumber(checkBalanceDTO.getAccNo())).thenReturn(account);

        // Act
        Object result = accountService.checkBalance(checkBalanceDTO);

        assertEquals(account.getBalance(), result);
    }

    @Test
    public void testCheckBalanceIncorrectMPIN() {
        // Arrange
        CheckBalanceDTO checkBalanceDTO = new CheckBalanceDTO();
        checkBalanceDTO.setAccNo(12345L);
        checkBalanceDTO.setMpin(1234);

        Account account=new Account();
        account.setMpin(1000);
        account.setBalance(10D);
        account.setAccountNumber(checkBalanceDTO.getAccNo());

        when(accountRepository.findByAccountNumber(checkBalanceDTO.getAccNo())).thenReturn(account);

        // Act
        Object result = accountService.checkBalance(checkBalanceDTO);

        // Assert
        assertEquals("Incorrect MPIN", result);
    }

    @Test
    public void testGetAllAccountsByUser() {
        List<Account> mockAccounts = new ArrayList<>();
        User user = new User();
        user.setEmail("test@example.com");
        Account account1 = new Account();
        Account account2 = new Account();
        account1.setUser(user);
        account2.setUser(user);
        mockAccounts.add(account1);
        mockAccounts.add(account2);

        // Configure accountRepository.findAccountNumbersByEmail() to return the mock list of accounts
        when(accountRepository.findAccountNumbersByEmail(user.getEmail())).thenReturn(mockAccounts);

        // Perform the test
        List<Account> accounts = accountService.getAllAccountsByUser(user);

        // Verify that accountRepository.findAccountNumbersByEmail() was called with the user's email
        verify(accountRepository, times(1)).findAccountNumbersByEmail(user.getEmail());

        // Verify that the returned list of accounts matches the mock list
        assertEquals(mockAccounts, accounts);
    }

    @Test
    public void testDepositMoneySuccess() {
        DepositMoneyDTO depositMoneyDTO = new DepositMoneyDTO();
        depositMoneyDTO.setAmount(100D);
        depositMoneyDTO.setMpin(1234);
        depositMoneyDTO.setAccNo(12345L);

        Account account=new Account();
        account.setMpin(1234);
        account.setBalance(10D);
        account.setAccountStatus(true);
        account.setAccountNumber(depositMoneyDTO.getAccNo());

        when(accountRepository.findByAccountNumber(depositMoneyDTO.getAccNo())).thenReturn(account);

        String result = accountService.depositMoney(depositMoneyDTO);
        assertEquals("Money deposited successfully!",result);
    }

    @Test
    public void testDepositMoneyFailure1() {
        DepositMoneyDTO depositMoneyDTO = new DepositMoneyDTO();
        depositMoneyDTO.setAmount(100D);
        depositMoneyDTO.setMpin(1000);
        depositMoneyDTO.setAccNo(12345L);

        Account account=new Account();
        account.setMpin(1234);
        account.setBalance(10D);
        account.setAccountStatus(true);
        account.setAccountNumber(depositMoneyDTO.getAccNo());

        when(accountRepository.findByAccountNumber(depositMoneyDTO.getAccNo())).thenReturn(account);

        String result = accountService.depositMoney(depositMoneyDTO);
        assertEquals("Incorrect MPIN",result);
    }

    @Test
    public void testDepositMoneyFailure2() {
        DepositMoneyDTO depositMoneyDTO = new DepositMoneyDTO();
        depositMoneyDTO.setAmount(100D);
        depositMoneyDTO.setMpin(1234);
        depositMoneyDTO.setAccNo(12345L);

        Account account=new Account();
        account.setMpin(1234);
        account.setBalance(10D);
        account.setAccountStatus(false);
        account.setAccountNumber(depositMoneyDTO.getAccNo());

        when(accountRepository.findByAccountNumber(depositMoneyDTO.getAccNo())).thenReturn(account);

        String result = accountService.depositMoney(depositMoneyDTO);
        assertEquals("Sorry! Your account has been disabled by admin",result);
    }

    @Test
    public void testWithdrawMoney() {
        // Create a mock account
        DepositMoneyDTO depositMoneyDTO = new DepositMoneyDTO();
        depositMoneyDTO.setAmount(100D);
        depositMoneyDTO.setMpin(1234);
        depositMoneyDTO.setAccNo(12345L);

        Account account=new Account();
        account.setMpin(1234);
        account.setBalance(1000D);
        account.setAccountStatus(true);
        account.setAccountNumber(depositMoneyDTO.getAccNo());

        // Configure accountRepository.findByAccountNumber() to return the mock account
        when(accountRepository.findByAccountNumber(depositMoneyDTO.getAccNo())).thenReturn(account);

        // Perform the test
        String result = accountService.withdrawMoney(depositMoneyDTO);

        // Verify the result
        assertEquals("Money withdrawn successfully!", result);
    }

    @Test
    public void testWithdrawMoneyFailure1() {
        // Create a mock account
        DepositMoneyDTO depositMoneyDTO = new DepositMoneyDTO();
        depositMoneyDTO.setAmount(1000D);
        depositMoneyDTO.setMpin(1234);
        depositMoneyDTO.setAccNo(12345L);

        Account account=new Account();
        account.setMpin(1234);
        account.setBalance(10D);
        account.setAccountStatus(true);
        account.setAccountNumber(depositMoneyDTO.getAccNo());

        // Configure accountRepository.findByAccountNumber() to return the mock account
        when(accountRepository.findByAccountNumber(depositMoneyDTO.getAccNo())).thenReturn(account);

        // Perform the test
        String result = accountService.withdrawMoney(depositMoneyDTO);

        // Verify the result
        assertEquals("Insufficient balance", result);
    }

    @Test
    public void testWithdrawMoneyFailure2() {
        // Create a mock account
        DepositMoneyDTO depositMoneyDTO = new DepositMoneyDTO();
        depositMoneyDTO.setAmount(100D);
        depositMoneyDTO.setMpin(1234);
        depositMoneyDTO.setAccNo(12345L);

        Account account=new Account();
        account.setMpin(1234);
        account.setBalance(1000D);
        account.setAccountStatus(false);
        account.setAccountNumber(depositMoneyDTO.getAccNo());

        // Configure accountRepository.findByAccountNumber() to return the mock account
        when(accountRepository.findByAccountNumber(depositMoneyDTO.getAccNo())).thenReturn(account);

        // Perform the test
        String result = accountService.withdrawMoney(depositMoneyDTO);

        // Verify the result
        assertEquals("Sorry! Your account has been disabled by admin", result);
    }

    @Test
    public void testTransferMoneySuccess() {
        TransferMoneyDTO transferMoneyDTO = new TransferMoneyDTO();
        transferMoneyDTO.setAmount(100D);
        transferMoneyDTO.setMpin(1234);
        transferMoneyDTO.setSenderAccNumber(12345L);
        transferMoneyDTO.setReceiverAccNumber(12312L);

        Account account=new Account();
        account.setMpin(1234);
        account.setBalance(100000D);
        account.setAccountStatus(true);
        account.setAccountNumber(transferMoneyDTO.getSenderAccNumber());

        when(accountRepository.findByAccountNumber(transferMoneyDTO.getSenderAccNumber())).thenReturn(account);

        String result = accountService.transferMoney(transferMoneyDTO);
        assertEquals("Money transferred successfully!",result);
    }

    @Test
    public void testTransferMoneyFailure1() {
        TransferMoneyDTO transferMoneyDTO = new TransferMoneyDTO();
        transferMoneyDTO.setAmount(100D);
        transferMoneyDTO.setMpin(1234);
        transferMoneyDTO.setSenderAccNumber(12345L);
        transferMoneyDTO.setReceiverAccNumber(12312L);

        Account account=new Account();
        account.setMpin(1234);
        account.setBalance(10D);
        account.setAccountStatus(true);
        account.setAccountNumber(transferMoneyDTO.getSenderAccNumber());

        when(accountRepository.findByAccountNumber(transferMoneyDTO.getSenderAccNumber())).thenReturn(account);

        String result = accountService.transferMoney(transferMoneyDTO);
        assertEquals("Insufficient balance",result);
    }

    @Test
    public void testTransferMoneyFailure2() {
        TransferMoneyDTO transferMoneyDTO = new TransferMoneyDTO();
        transferMoneyDTO.setAmount(100D);
        transferMoneyDTO.setMpin(1234);
        transferMoneyDTO.setSenderAccNumber(12345L);
        transferMoneyDTO.setReceiverAccNumber(12345L);

        Account account=new Account();
        account.setMpin(1234);
        account.setBalance(100000D);
        account.setAccountStatus(true);
        account.setAccountNumber(transferMoneyDTO.getSenderAccNumber());

        when(accountRepository.findByAccountNumber(transferMoneyDTO.getSenderAccNumber())).thenReturn(account);

        String result = accountService.transferMoney(transferMoneyDTO);
        assertEquals("Cannot transfer money to same account",result);
    }

    @Test
    public void testChangeAccountStatusAsAdmin() {
        // Create a mock account
        Account account = new Account();
        account.setAccountStatus(true);
        account.setAccountNumber(1234L);
        User user = new User();
        user.setEmail("admin6@gmail.com");
        AccountNoDTO accountNoDTO = new AccountNoDTO();
        accountNoDTO.setAccNo(1234L);

        // Configure accountRepository.findByAccountNumber() to return the mock account
        when(accountRepository.findByAccountNumber(accountNoDTO.getAccNo())).thenReturn(account);

        // Perform the test
        Boolean result = accountService.changeAccountStatus(accountNoDTO, user);

        // Verify that accountRepository.findByAccountNumber() was called with the account number from accountNoDTO
        verify(accountRepository, times(1)).findByAccountNumber(accountNoDTO.getAccNo());

        // Verify that the account status was toggled
        assertFalse(result);
    }

    @Test
    public void testChangeAccountStatusAsNonAdmin() {
        Account account = new Account();
        account.setAccountStatus(true);
        account.setAccountNumber(1234L);
        User user = new User();
        user.setEmail("user@example.com");
        AccountNoDTO accountNoDTO = new AccountNoDTO();
        accountNoDTO.setAccNo(1234L);

        // Perform the test
        Boolean result = accountService.changeAccountStatus(accountNoDTO, user);

        // Verify that accountRepository.findByAccountNumber() was not called
        verify(accountRepository, never()).findByAccountNumber(anyLong());

        // Verify that the result is null since the user is not an admin
        assertNull(result);
    }
}