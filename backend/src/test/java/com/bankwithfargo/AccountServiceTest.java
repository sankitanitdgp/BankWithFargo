package com.bankwithfargo.service;

import com.bankwithfargo.dto.AccountRequestDTO;
import com.bankwithfargo.dto.CheckBalanceDTO;
import com.bankwithfargo.model.Account;
import com.bankwithfargo.model.Transaction;
import com.bankwithfargo.model.User;
import com.bankwithfargo.repository.AccountRepository;
import com.bankwithfargo.repository.TransactionRepository;
import com.bankwithfargo.repository.UserLoginRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
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
        // Arrange
        AccountRequestDTO accountRequestDTO = new AccountRequestDTO();
        accountRequestDTO.setTitle("Mr.");
        accountRequestDTO.setFirstName("John");
        accountRequestDTO.setLastName("Doe");
        accountRequestDTO.setDob(LocalDate.now());
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

        when(accountRepository.save(any(Account.class))).thenReturn(newAccount);

        // Act
        Account openedAccount = accountService.openAccount(accountRequestDTO, user);

        // Assert
        assertNotNull(openedAccount);
        assertTrue(openedAccount.getAccountStatus());
        assertEquals("IFSC0099123", openedAccount.getIfsc());
        assertEquals(0.0, openedAccount.getBalance());
        assertEquals(LocalDate.now(), openedAccount.getDateOfOpening());
        assertEquals(user.getEmail(), openedAccount.getEmail());
        assertEquals(user, openedAccount.getUser());
    }

    @Test
    public void testGetTransactions() {
        // Arrange
        Long accNo = 12345L;

        List<Transaction> transactions = new ArrayList<>();
        when(transactionRepository.findAllBySenderAccNoOrReceiverAccNo(accNo, accNo)).thenReturn(transactions);

        // Act
        List<Transaction> result = accountService.getTransactions(accNo);

        // Assert
        assertNotNull(result);
        assertEquals(transactions, result);
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

        // Act
        Object result = accountService.checkBalance(checkBalanceDTO);

        // Assert
        assertEquals(account.getBalance(), result);
    }

    @Test
    public void testCheckBalanceAccountDoesNotExist() {
        // Arrange
        CheckBalanceDTO checkBalanceDTO = new CheckBalanceDTO();
        checkBalanceDTO.setAccNo(12345L);

        when(accountRepository.findByAccountNumber(checkBalanceDTO.getAccNo())).thenReturn(null);

        // Act
        Object result = accountService.checkBalance(checkBalanceDTO);

        // Assert
        assertEquals("Account does not exist.", result);
    }

    @Test
    public void testCheckBalanceIncorrectMPIN() {
        // Arrange
        CheckBalanceDTO checkBalanceDTO = new CheckBalanceDTO();
        checkBalanceDTO.setAccNo(12345L);
        checkBalanceDTO.setMpin(1234);

        Account account = new Account();
        account.setAccountNumber(checkBalanceDTO.getAccNo());
        account.setMpin(5678);

        when(accountRepository.findByAccountNumber(checkBalanceDTO.getAccNo())).thenReturn(account);

        // Act
        Object result = accountService.checkBalance(checkBalanceDTO);

        // Assert
        assertEquals("Incorrect MPIN", result);
    }

    // Similar tests for other methods: getAllAccounts, depositMoney, withdrawMoney
}
