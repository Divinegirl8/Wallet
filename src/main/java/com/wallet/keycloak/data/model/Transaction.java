package com.wallet.keycloak.data.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.AUTO;
import static jakarta.persistence.GenerationType.UUID;

@ToString
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = AUTO)
    private Long id;
    private String description;
    private String senderName;
    private BigDecimal amount;
    private LocalDateTime date;
    private Long userId;
    @ManyToOne
    private WalletAccount walletAccount;
    @Enumerated(EnumType.STRING)
    private Status status;
}
