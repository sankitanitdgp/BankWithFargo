package com.bankwithfargo.controllers;

import com.bankwithfargo.dto.AccountNoDTO;
import com.bankwithfargo.dto.UserLoginRequestDTO;
import com.bankwithfargo.dto.UserSignupRequestDTO;
import com.bankwithfargo.entity.User;
import com.bankwithfargo.exception.InsufficientAccessException;
import com.bankwithfargo.service.AuthService;
import com.bankwithfargo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController

public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AuthService authService;
  
    @RequestMapping(value="/getAllUsers", method=POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> getAllUsers(@AuthenticationPrincipal User user) {
        try {
            return new ResponseEntity<Object>(userService.getAllUsers(user), HttpStatus.OK);
        } catch (Exception e){
            throw new InsufficientAccessException("User does not have admin role");
        }
    }

    @RequestMapping(value="/loginUser", method=POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> authorizeUser(@Valid @RequestBody UserLoginRequestDTO user) {
        return new ResponseEntity<Object>(authService.authorizeUser(user), HttpStatus.OK);
    }

    @RequestMapping(value="/addUser", method=POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserSignupRequestDTO user) {
        return new ResponseEntity<Object>(userService.createUser(user), HttpStatus.CREATED);
    }

    @RequestMapping(value="/getUserByAccountNo", method=POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> getUserByAccountNo(@RequestBody AccountNoDTO accountNoDTO,
                                                     @AuthenticationPrincipal User user) {
        return new ResponseEntity<Object>(userService.getUserByAccountNo(accountNoDTO,user), HttpStatus.OK);
    }
}