package com.wallet.keycloak.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
public class RegisterResponse {
    private String message;
    private String accessToken;
    private String refreshToken;
    private String userId;
}
