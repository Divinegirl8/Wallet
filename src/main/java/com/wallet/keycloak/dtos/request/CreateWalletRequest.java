package com.wallet.keycloak.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateWalletRequest {
    private String walletNumber;
    private String fullName;
    private String pin;
}
