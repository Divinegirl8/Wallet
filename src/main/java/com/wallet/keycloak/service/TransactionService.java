package com.wallet.keycloak.service;

import com.wallet.keycloak.data.model.Transaction;
import com.wallet.keycloak.dtos.request.CreateTransactionRequest;
import com.wallet.keycloak.exception.WalletNotFoundException;


import java.util.List;

public interface TransactionService {
    Transaction createTransaction(CreateTransactionRequest transactionRequest,Long userId) throws WalletNotFoundException;
    List<Transaction> findAllTransactions();
}
