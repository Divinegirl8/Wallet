package com.wallet.keycloak.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class LoginResponse {
    private String message;
    private String accessToken;
    private String refreshToken;

}
