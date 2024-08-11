package com.wallet.keycloak.data.model;


import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "wallet_user")
@ToString
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String emailAddress;
    private String phoneNumber;
    private String walletPin;
    @OneToOne
    private WalletAccount walletAccount;
}
