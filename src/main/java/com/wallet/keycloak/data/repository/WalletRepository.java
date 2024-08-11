package com.wallet.keycloak.data.repository;

import com.wallet.keycloak.data.model.WalletAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<WalletAccount, Long> {
    WalletAccount findByWalletNumber(String walletNUmber);
}
