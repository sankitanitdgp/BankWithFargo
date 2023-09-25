//package com.bankwithfargo.service;
//
//import com.bankwithfargo.dto.UserSignupRequestDTO;
//import com.bankwithfargo.entity.Account;
//import com.bankwithfargo.entity.User;
//import com.bankwithfargo.repository.UserLoginRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.ResponseEntity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class UserServiceTest {
//
//    @Mock
//    private UserLoginRepository userRepository;
//
//    @InjectMocks
//    private UserService userService;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testGetAllUsers() {
//        // Arrange
//        List<User> users = new ArrayList<>();
//        users.add(new User());
//        when(userRepository.findAll()).thenReturn(users);
//
//        User user=new User();
//        user.setEmail("admin6@gmail.com");
//
//        List<Account> result = userService.getAllUsers(user);
//
//        // Assert
//        assertEquals(users.size(), result.size());
//    }
//
//    @Test
//    public void testCreateUser_Success() {
//        UserSignupRequestDTO userDTO = new UserSignupRequestDTO();
//        userDTO.setEmail("test@example.com");
//        userDTO.setPassword("password");
//
//        // Mock the behavior of userRepository.findOneByEmail
//        when(userRepository.findOneByEmail(userDTO.getEmail())).thenReturn(null);
//
//        // Mock the behavior of userRepository.save
//        when(userRepository.save(any(User.class))).thenReturn(new User());
//
//        ResponseEntity createdUser = userService.createUser(userDTO);
//
//        assertNotNull(createdUser);
//        assertEquals(userDTO.getEmail(), createdUser.getEmail());
//        assertEquals(userDTO.getPassword(), createdUser.getPassword());
//
//        // Verify that userRepository.findOneByEmail was called once
//        verify(userRepository, times(1)).findOneByEmail(userDTO.getEmail());
//
//        // Verify that userRepository.save was called once
//        verify(userRepository, times(1)).save(any(User.class));
//    }
//
//    @Test
//    public void testCreateUser_UserAlreadyExists() {
//        UserSignupRequestDTO userDTO = new UserSignupRequestDTO();
//        userDTO.setEmail("test@example.com");
//        userDTO.setPassword("password");
//
//        // Mock the behavior of userRepository.findOneByEmail to return an existing user
//        when(userRepository.findOneByEmail(userDTO.getEmail())).thenReturn(new User());
//
//        ResponseEntity createdUser = userService.createUser(userDTO);
//
//        assertNull(createdUser);
//
//        // Verify that userRepository.findOneByEmail was called once
//        verify(userRepository, times(1)).findOneByEmail(userDTO.getEmail());
//
//        // Verify that userRepository.save was never called
//        verify(userRepository, never()).save(any(User.class));
//    }
//}
