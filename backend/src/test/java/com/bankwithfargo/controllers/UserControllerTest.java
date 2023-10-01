package com.bankwithfargo.controllers;

import com.bankwithfargo.dto.AccountNoDTO;
import com.bankwithfargo.dto.UserLoginRequestDTO;
import com.bankwithfargo.dto.UserSignupRequestDTO;
import com.bankwithfargo.entity.Account;
import com.bankwithfargo.entity.User;
import com.bankwithfargo.exception.InsufficientAccessException;
import com.bankwithfargo.service.AuthService;
import com.bankwithfargo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Mock
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        User mockUser = new User();
        List<Account> accounts=new ArrayList<>();
        ResponseEntity<Object> mockResponseEntity = new ResponseEntity<>(accounts, HttpStatus.OK);

        when(userService.getAllUsers(mockUser)).thenReturn(accounts);

        // Perform the test
        ResponseEntity<Object> response = userController.getAllUsers(mockUser);

        // Verify that userService.getAllUsers() was called with the mockUser object
        verify(userService, times(1)).getAllUsers(mockUser);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(accounts, response.getBody());
    }

    @Test
    public void testGetAllUsersWithException() {
        User mockUser=new User();
        // Configure userService.getAllUsers() to throw an exception
        when(userService.getAllUsers(mockUser)).thenThrow(new RuntimeException("Some error"));

        // Perform the test and expect an exception to be thrown
        assertThrows(InsufficientAccessException.class, () -> {
            userController.getAllUsers(mockUser);
        });
    }

    @Test
    public void testAuthorizeUser() {
        UserLoginRequestDTO userLoginRequestDTO = new UserLoginRequestDTO();

        // Configure authService.authorizeUser() to return the mock response
        when(authService.authorizeUser(userLoginRequestDTO)).thenReturn("token");

        // Perform the test
        ResponseEntity<Object> response = userController.authorizeUser(userLoginRequestDTO);

        // Verify that authService.authorizeUser() was called with the mock UserLoginRequestDTO
        verify(authService, times(1)).authorizeUser(userLoginRequestDTO);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("token", response.getBody());
    }


    @Test
    public void testAddUserSuccess() {
        // Create a mock UserSignupRequestDTO
        UserSignupRequestDTO mockUser = new UserSignupRequestDTO();
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("password");

        // Create a mock ResponseEntity from the userService.createUser method
        ResponseEntity<Object> mockResponse = new ResponseEntity<>(mockUser, HttpStatus.CREATED);

        // Configure userService.createUser to return the mock response
        when(userService.createUser(mockUser)).thenReturn(mockResponse);

        // Perform the test
        ResponseEntity<Object> response = userController.addUser(mockUser);

        // Verify that userService.createUser was called with the mockUser object
        verify(userService, times(1)).createUser(mockUser);

        // Verify the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(new ResponseEntity(mockUser, response.getStatusCode()), response.getBody());
    }

    @Test
    public void testGetUserByAccountNoSuccess() {
        AccountNoDTO accountNoDTO = new AccountNoDTO();
        accountNoDTO.setAccNo(1234L);
        User user = new User();
        user.setEmail("admin6@gmail.com");

        Account account = new Account();
        account.setAccountNumber(1234L);

        // Configure userService.getUserByAccountNo() to return the mock response
        when(userService.getUserByAccountNo(accountNoDTO, user)).thenReturn(account);

        // Perform the test
        ResponseEntity<Object> response = userController.getUserByAccountNo(accountNoDTO, user);

        // Verify that userService.getUserByAccountNo() was called with the mock AccountNoDTO and User
        verify(userService, times(1)).getUserByAccountNo(accountNoDTO, user);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(account, response.getBody());
    }

    @Test
    public void testGetUserByAccountNoFailure() {
        AccountNoDTO accountNoDTO = new AccountNoDTO();
        accountNoDTO.setAccNo(1234L);
        User user = new User();
        user.setEmail("test@example.com");

        Account account = new Account();
        account.setAccountNumber(1234L);

        // Configure userService.getUserByAccountNo() to return the mock response
        when(userService.getUserByAccountNo(accountNoDTO, user)).thenReturn(null);

        // Perform the test
        ResponseEntity<Object> response = userController.getUserByAccountNo(accountNoDTO, user);

        // Verify that userService.getUserByAccountNo() was called with the mock AccountNoDTO and User
        verify(userService, times(1)).getUserByAccountNo(accountNoDTO, user);

        // Verify the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNull(response.getBody());
    }

}

