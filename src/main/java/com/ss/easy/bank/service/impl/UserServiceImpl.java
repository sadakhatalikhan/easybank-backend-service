package com.ss.easy.bank.service.impl;

import com.ss.easy.bank.model.Customer;
import com.ss.easy.bank.repository.CustomerRepository;
import com.ss.easy.bank.request.CustomerRequest;
import com.ss.easy.bank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.ss.easy.bank.mapper.EasyBankHelper.modelMapper;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<String> createUser(CustomerRequest customerRequest) {
        try {
            customerRequest.setPwd(passwordEncoder.encode(customerRequest.getPwd()));
            Customer savedCustomer = customerRepository.save(modelMapper(customerRequest));
            if (savedCustomer.getId() > 0) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body("Customer is successfully created");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Customer registration failed");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Exception occurred while storing the customer " + e.getMessage());
        }
    }
}
