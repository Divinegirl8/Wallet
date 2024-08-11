package com.wallet.keycloak.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class FundWalletRequest {
    private BigDecimal amount;
    private Long userId;
    private String description;
    private String senderName;

}
