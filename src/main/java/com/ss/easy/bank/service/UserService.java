package com.ss.easy.bank.service;

import com.ss.easy.bank.request.CustomerRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<String> createUser(CustomerRequest customerRequest);
}
