package com.ss.easy.bank.controller;

import com.ss.easy.bank.request.CustomerRequest;
import com.ss.easy.bank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/create/user")
    public ResponseEntity<String> createUser(@RequestBody CustomerRequest customerRequest) {
        return userService.createUser(customerRequest);
    }

}
