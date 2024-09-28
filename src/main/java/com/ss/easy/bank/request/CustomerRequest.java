package com.ss.easy.bank.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with", builderMethodName = "builder")
@ToString
public class CustomerRequest {
    private long id;
    private String email;
    private String pwd;
    private String role;
}
