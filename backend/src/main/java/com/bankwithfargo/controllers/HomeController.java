package com.bankwithfargo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/home")
    public String welcome() {
        return "Welcome to java 2024 !!";
    }
}
