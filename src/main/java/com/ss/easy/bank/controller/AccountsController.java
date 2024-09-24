package com.ss.easy.bank.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountsController {

    @GetMapping("/accounts")
    public String getAccounts() {
        return "Fetch account details";
    }
}
