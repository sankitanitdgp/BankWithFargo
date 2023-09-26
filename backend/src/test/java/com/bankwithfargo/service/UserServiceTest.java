package com.bankwithfargo.service;

import com.bankwithfargo.dto.AccountNoDTO;
import com.bankwithfargo.dto.AccountRequestDTO;
import com.bankwithfargo.dto.UserSignupRequestDTO;
import com.bankwithfargo.entity.Account;
import com.bankwithfargo.entity.User;
import com.bankwithfargo.repository.AccountRepository;
import com.bankwithfargo.repository.UserLoginRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserLoginRepository userRepository;
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllUsersSuccess() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account());
        when(accountRepository.findAll()).thenReturn(accounts);

        User user=new User();
        user.setEmail("admin6@gmail.com");

        List<Account> result = userService.getAllUsers(user);

        assertEquals(accounts.size(), result.size());
        assertEquals(accounts,result);
    }

    @Test
    public void testGetAllUsersFailure() {
        List<Account> accounts = new ArrayList<>();
        accounts.add(new Account());
        when(accountRepository.findAll()).thenReturn(accounts);

        User user=new User();
        user.setEmail("abc@gmail.com"); //Not admin account
        List<Account> result = userService.getAllUsers(user);

        assertNull(result);
    }

    @Test
    public void testCreateUser_Success() {
        UserSignupRequestDTO userDTO = new UserSignupRequestDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");

        when(userRepository.findOneByEmail(userDTO.getEmail())).thenReturn(null);

        User user=new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(user.getPassword());

        when(userRepository.save(any(User.class))).thenReturn(user);

        ResponseEntity<Object> createdUser = userService.createUser(userDTO);

        assertNotNull(createdUser);
        assertEquals(ResponseEntity.ok(user),createdUser);
        verify(userRepository, times(1)).findOneByEmail(userDTO.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testCreateUser_UserAlreadyExists() {
        UserSignupRequestDTO userDTO = new UserSignupRequestDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");

        User user=new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(user.getPassword());

        when(userRepository.findOneByEmail(userDTO.getEmail())).thenReturn(user);

        ResponseEntity<Object> createdUser = userService.createUser(userDTO);

        assertEquals(new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST),createdUser);
        verify(userRepository, times(1)).findOneByEmail(userDTO.getEmail());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void testGetUserByAccountNumberSuccess() {
        AccountNoDTO accountNoDTO = new AccountNoDTO();
        accountNoDTO.setAccNo(12345L);
        Account newAccount = new Account();
        newAccount.setTitle("Mr.");
        newAccount.setFirstName("John");
        newAccount.setLastName("Doe");
        newAccount.setDob(new Date(2023,9,20));
        newAccount.setPhone("1234567890");
        newAccount.setPresentAddress("D");
        newAccount.setPermanentAddress("John");
        newAccount.setPan("qwert4567y");
        newAccount.setOccupationType("qwe");
        newAccount.setIncome(12345L);
        newAccount.setEmail("john@example.com");
        newAccount.setMpin(1234);

        User user = new User();
        user.setEmail("john@example.com");

        newAccount.setAccountNumber(12345L);
        newAccount.setAccountStatus((true));
        newAccount.setDateOfOpening(new Date(2023,9,20));
        newAccount.setIfsc("IFSC0099123");
        newAccount.setBalance(0.0);
        newAccount.setUser(user);
        BeanUtils.copyProperties(accountNoDTO, newAccount);

        when(accountRepository.findByAccountNumber(accountNoDTO.getAccNo())).thenReturn(newAccount);

        User admin=new User();
        admin.setEmail("admin6@gmail.com");

        Account result = userService.getUserByAccountNo(accountNoDTO,admin);

        assertEquals(newAccount,result);
    }

    @Test
    public void testGetUserByAccountNumberFailure() {
        AccountNoDTO accountNoDTO = new AccountNoDTO();
        accountNoDTO.setAccNo(12345L);
        Account newAccount = new Account();
        newAccount.setTitle("Mr.");
        newAccount.setFirstName("John");
        newAccount.setLastName("Doe");
        newAccount.setDob(new Date(2023,9,20));
        newAccount.setPhone("1234567890");
        newAccount.setPresentAddress("D");
        newAccount.setPermanentAddress("John");
        newAccount.setPan("qwert4567y");
        newAccount.setOccupationType("qwe");
        newAccount.setIncome(12345L);
        newAccount.setEmail("john@example.com");
        newAccount.setMpin(1234);

        User user = new User();
        user.setEmail("john@example.com");

        newAccount.setAccountNumber(12345L);
        newAccount.setAccountStatus((true));
        newAccount.setDateOfOpening(new Date(2023,9,20));
        newAccount.setIfsc("IFSC0099123");
        newAccount.setBalance(0.0);
        newAccount.setUser(user);
        BeanUtils.copyProperties(accountNoDTO, newAccount);

        when(accountRepository.findByAccountNumber(accountNoDTO.getAccNo())).thenReturn(newAccount);

        User user1=new User();
        user1.setEmail("notadmin@gmail.com");

        Account result = userService.getUserByAccountNo(accountNoDTO,user1);

        assertNull(result);
    }
}
