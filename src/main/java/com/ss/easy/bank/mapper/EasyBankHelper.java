package com.ss.easy.bank.mapper;

import com.ss.easy.bank.model.Customer;
import com.ss.easy.bank.request.CustomerRequest;

public class EasyBankHelper {
    public static Customer modelMapper(CustomerRequest customerRequest) {
        return Customer.builder()
                .withEmail(customerRequest.getEmail())
                .withRole(customerRequest.getRole())
                .withPwd(customerRequest.getPwd())
                .build();
    }
}
