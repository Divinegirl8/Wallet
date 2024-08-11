package com.wallet.keycloak.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CreateTransactionRequest {
    private String description;
    private String senderName;
    private BigDecimal amount;
}
