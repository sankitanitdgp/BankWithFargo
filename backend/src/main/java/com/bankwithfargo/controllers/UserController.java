package com.bankwithfargo.controllers;

import com.bankwithfargo.dto.UserLoginRequestDTO;
import com.bankwithfargo.dto.UserSignupRequestDTO;
import com.bankwithfargo.model.User;
import com.bankwithfargo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


@RestController

public class UserController {
    @Autowired
    UserService userService;
  
    @RequestMapping(value="/getAllUsers", method=GET, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> getAllUsers() {
        return new ResponseEntity<Object>(userService.getAllUsers(), HttpStatus.OK);
    }

    @RequestMapping(value="/loginUser", method=POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> authorizeUser(@Valid @RequestBody UserLoginRequestDTO user) {
        return new ResponseEntity<Object>(userService.authorizeUser(user), HttpStatus.OK);
    }

//    @PostMapping("/authenticate")
//    public String generateToken(@RequestBody AuthRequestDTO authRequest) throws Exception {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword())
//            );
//        } catch (Exception ex) {
//            throw new Exception("inavalid username/password");
//        }
//        return jwtUtil.generateToken(authRequest.getUserName());
//    }


//    @GetMapping(value = "/validate")
//    public boolean getValidation(@RequestHeader("Authorization") String token){
//        token = token.substring(7);
//        AuthResponseDTO auth = new AuthResponseDTO();
//
//        //log.info("Token validation for "+jwtUtil.extractUsername(token));
//
//        if(jwtUtil.validateToken(token)) {
//
//            System.out.println("Token validated");
//            return true;
//        }
//        else {
//            System.out.println("Token NOT validated");
//            return false;
//
//        }
//    }

    @RequestMapping(value="/addUser", method=POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserSignupRequestDTO user) {
        return new ResponseEntity<Object>(userService.createUser(user), HttpStatus.CREATED);
    }
}