package com.wallet.keycloak.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.math.BigDecimal;

import static jakarta.persistence.GenerationType.IDENTITY;

@ToString
@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletAccount {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String walletNumber;
    private String fullName;
    private String pin;
    private BigDecimal balance = BigDecimal.ZERO;

}
