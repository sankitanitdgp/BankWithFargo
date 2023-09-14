package com.bankwithfargo.controllers;

import com.bankwithfargo.dto.AccountRequestDTO;
import com.bankwithfargo.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/openAccount", method = POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<Object> openAccount(@Valid @RequestBody AccountRequestDTO accountRequestDTO) {
        return new ResponseEntity<Object>(accountService.openAccount(accountRequestDTO), HttpStatus.CREATED);
    }
}
