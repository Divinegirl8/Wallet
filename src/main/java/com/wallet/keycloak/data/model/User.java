package com.wallet.keycloak.data.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "wallet_user")
public class User {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
    private String password;
    private String emailAddress;
}
