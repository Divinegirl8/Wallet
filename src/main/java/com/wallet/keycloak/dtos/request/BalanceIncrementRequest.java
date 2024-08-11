package com.wallet.keycloak.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BalanceIncrementRequest {
    private String reference;
    private Long transactionId;
}
