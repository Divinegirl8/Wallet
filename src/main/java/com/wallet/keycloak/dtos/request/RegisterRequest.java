package com.wallet.keycloak.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterRequest {
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String emailAddress;
}
