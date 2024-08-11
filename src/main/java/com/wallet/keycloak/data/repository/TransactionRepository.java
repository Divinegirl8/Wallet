package com.wallet.keycloak.data.repository;

import com.wallet.keycloak.data.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
