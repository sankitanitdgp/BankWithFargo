package com.bankwithfargo.service;

import com.bankwithfargo.dto.UserLoginRequestDTO;
import com.bankwithfargo.entity.User;
import com.bankwithfargo.repository.UserLoginRepository;
import com.bankwithfargo.security.JwtTokenProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private UserLoginRepository userRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthorizeUserWithValidCredentials() {
        // Arrange
        UserLoginRequestDTO userDTO = new UserLoginRequestDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");

        User foundUser = new User();
        foundUser.setEmail("test@example.com");
        foundUser.setPassword("password");

        when(userRepository.findOneByEmail(userDTO.getEmail())).thenReturn(foundUser);
        when(jwtTokenProvider.generateToken(userDTO.getEmail())).thenReturn("token");

        // Act
        String token = authService.authorizeUser(userDTO);

        // Assert
        assertEquals("token", token);
    }

    @Test
    public void testAuthorizeUserWithIncorrectPassword() {
        // Arrange
        UserLoginRequestDTO userDTO = new UserLoginRequestDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");

        User foundUser = new User();
        foundUser.setEmail("test@example.com");
        foundUser.setPassword("wrong_password");

        when(userRepository.findOneByEmail(userDTO.getEmail())).thenReturn(foundUser);

        // Act
        String result = authService.authorizeUser(userDTO);

        // Assert
        assertEquals("Incorrect password", result);
    }

    @Test
    public void testAuthorizeUserWithUserNotFound() {
        // Arrange
        UserLoginRequestDTO userDTO = new UserLoginRequestDTO();
        userDTO.setEmail("nonexistent@example.com");
        userDTO.setPassword("password");

        when(userRepository.findOneByEmail(userDTO.getEmail())).thenReturn(null);

        // Act
        String result = authService.authorizeUser(userDTO);

        // Assert
        assertEquals("User not found", result);
    }

    @Test
    public void testAuthorizeUserWithException() {
        // Arrange
        UserLoginRequestDTO userDTO = new UserLoginRequestDTO();
        userDTO.setEmail("test@example.com");
        userDTO.setPassword("password");

        when(userRepository.findOneByEmail(userDTO.getEmail())).thenThrow(new RuntimeException("Simulated exception"));

        // Act
        String result = authService.authorizeUser(userDTO);

        // Assert
        assertNull(result); // Since there's an exception, the result should be null
    }
}
