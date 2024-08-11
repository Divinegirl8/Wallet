package com.wallet.keycloak.service.implementation;

import com.wallet.keycloak.data.model.Status;
import com.wallet.keycloak.data.model.Transaction;
import com.wallet.keycloak.data.model.User;
import com.wallet.keycloak.data.model.WalletAccount;
import com.wallet.keycloak.data.repository.TransactionRepository;
import com.wallet.keycloak.data.repository.UserRepository;
import com.wallet.keycloak.data.repository.WalletRepository;
import com.wallet.keycloak.dtos.request.CreateTransactionRequest;
import com.wallet.keycloak.exception.WalletNotFoundException;
import com.wallet.keycloak.service.AuthenticationService;
import com.wallet.keycloak.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceApp implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;

    @Override
    public Transaction createTransaction(CreateTransactionRequest transactionRequest,Long userId) throws WalletNotFoundException {
        User user = userRepository.findById(userId).orElseThrow(()-> new UsernameNotFoundException("user does not exist"));
        WalletAccount walletAccount = walletRepository.findById(user.getWalletAccount().getId()).orElseThrow(() -> new WalletNotFoundException("wallet not found"));

        var transaction = Transaction.builder()
                .senderName(transactionRequest.getSenderName())
                .description(transactionRequest.getDescription())
                .amount(transactionRequest.getAmount())
                .date(LocalDateTime.now())
                .userId(user.getId())
                .walletAccount(walletAccount)
                .status(Status.PENDING)
                .build();

        transactionRepository.save(transaction);

        return transaction;
    }

    @Override
    public List<Transaction> findAllTransactions() {
        return transactionRepository.findAll();
    }
}
