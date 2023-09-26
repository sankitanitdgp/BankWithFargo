package com.bankwithfargo.controllers;

import com.bankwithfargo.dto.UserLoginRequestDTO;
import com.bankwithfargo.dto.UserSignupRequestDTO;
import com.bankwithfargo.entity.User;
import com.bankwithfargo.service.AuthService;
import com.bankwithfargo.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        User user = new User(); // Create a mock User
        when(userService.getAllUsers(user)).thenReturn(Collections.emptyList()); // Mock the userService

        ResponseEntity<Object> response = userController.getAllUsers(user);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testAuthorizeUser() {
        UserLoginRequestDTO userLoginRequestDTO = new UserLoginRequestDTO(); // Create a mock UserLoginRequestDTO
        when(authService.authorizeUser(userLoginRequestDTO)).thenReturn(String.valueOf(new ResponseEntity<>(HttpStatus.OK))); // Mock the authService

        ResponseEntity<Object> response = userController.authorizeUser(userLoginRequestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testAddUser() {
        UserSignupRequestDTO userSignupRequestDTO = new UserSignupRequestDTO(); // Create a mock UserSignupRequestDTO
        when(userService.createUser(userSignupRequestDTO)).thenReturn((new ResponseEntity<>(HttpStatus.CREATED))); // Mock the userService

        ResponseEntity<Object> response = userController.addUser(userSignupRequestDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

}

