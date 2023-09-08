package com.bankwithfargo.controllers;

import com.bankwithfargo.model.User;
import com.bankwithfargo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value="/verify", method=POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> authorizeUser(@RequestBody User user) {
        return new ResponseEntity<Object>(userService.authorizeUser(user), HttpStatus.OK);
    }

    @RequestMapping(value="/add", method=POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> addUser(@RequestBody User user) {
        return new ResponseEntity<Object>(userService.createUser(user), HttpStatus.OK);
    }
}