package com.wallet.keycloak.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wallet.keycloak.data.model.Status;
import com.wallet.keycloak.data.model.Transaction;
import com.wallet.keycloak.data.model.User;
import com.wallet.keycloak.data.model.WalletAccount;
import com.wallet.keycloak.data.repository.TransactionRepository;
import com.wallet.keycloak.data.repository.UserRepository;
import com.wallet.keycloak.data.repository.WalletRepository;
import com.wallet.keycloak.dtos.request.BalanceIncrementRequest;
import com.wallet.keycloak.dtos.request.CreateTransactionRequest;
import com.wallet.keycloak.dtos.request.CreateWalletRequest;
import com.wallet.keycloak.dtos.request.FundWalletRequest;
import com.wallet.keycloak.dtos.response.ApiResponse;
import com.wallet.keycloak.dtos.response.FundWalletResponse;
import com.wallet.keycloak.dtos.response.InitializePaymentResponse;
import com.wallet.keycloak.dtos.response.TransactionNotFoundException;
import com.wallet.keycloak.exception.PhoneNumberExistException;
import com.wallet.keycloak.exception.WalletNotFoundException;
import com.wallet.keycloak.service.AuthenticationService;
import com.wallet.keycloak.service.PayStackService;
import com.wallet.keycloak.service.TransactionService;
import com.wallet.keycloak.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class WalletServiceApp implements WalletService {
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final PayStackService payStackService;
   private final TransactionService transactionService;
   private final TransactionRepository transactionRepository;

    @Override
    public WalletAccount createWalletAccount(CreateWalletRequest createWalletRequest) throws PhoneNumberExistException {
        if (walletAccountExist(createWalletRequest.getWalletNumber())){
            throw new PhoneNumberExistException("Phone number exist, kindly provide another phone number");
        }

        var wallet = WalletAccount.builder()
                .walletNumber(createWalletRequest.getWalletNumber())
                .balance(BigDecimal.ZERO)
                .fullName(createWalletRequest.getFullName())
                .pin(createWalletRequest.getPin())
                .build();
        walletRepository.save(wallet);


        return wallet;
    }

    @Override
    public WalletAccount findWalletById(Long id) throws WalletNotFoundException {
        return walletRepository.findById(id).orElseThrow(() -> new WalletNotFoundException("wallet not found"));
    }

    @Override
    public FundWalletResponse fundWallet(FundWalletRequest request) throws JsonProcessingException, WalletNotFoundException {
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new UsernameNotFoundException("user not found"));


        InitializePaymentResponse response = payStackService.initializePayment(user.getEmailAddress(), request.getAmount());

        CreateTransactionRequest transactionRequest = createTransactionRequest(request.getAmount(),request.getSenderName(),request.getDescription());

       Transaction transaction = transactionService.createTransaction(transactionRequest, user.getId());


       return new FundWalletResponse(transaction,response.getReference(),response.getUrl());


    }

    @Override
    public String verifyPayment(String reference) {
        ApiResponse<?> res = payStackService.verifyPayment(reference);
        String status = null;
        String responseString = res.getData().toString();
        Pattern pattern = Pattern.compile("status=(\\w+)");
        Matcher matcher = pattern.matcher(responseString);
        if (matcher.find()) {
            status = matcher.group(1);
        }

        return status;
    }

    @Override
    public Transaction balanceIncrement(BalanceIncrementRequest balanceIncrementRequest) throws TransactionNotFoundException, WalletNotFoundException {

        String status = verifyPayment(balanceIncrementRequest.getReference()).toLowerCase();


        Transaction transaction = transactionRepository.findById(balanceIncrementRequest.getTransactionId())
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));


        WalletAccount walletAccount = walletRepository.findById(transaction.getWalletAccount().getId())
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));

        switch (status) {
            case "success" -> {
                transaction.setStatus(Status.SUCCESSFUL);
                BigDecimal newBalance = walletAccount.getBalance().add(transaction.getAmount());
                walletAccount.setBalance(newBalance);
            }
            case "failed" -> transaction.setStatus(Status.FAILURE);
            default -> transaction.setStatus(Status.PENDING);
        }


        walletRepository.save(walletAccount);
        transactionRepository.save(transaction);

        return transaction;
    }


    @Override
    public BigDecimal checkBalance(Long walletId) throws WalletNotFoundException {
        WalletAccount walletAccount = walletRepository.findById(walletId).orElseThrow(() -> new WalletNotFoundException("wallet not found"));
        return walletAccount.getBalance();
    }


    private boolean walletAccountExist(String walletAccountNumber){
        WalletAccount walletAccount = walletRepository.findByWalletNumber(walletAccountNumber);

        return walletAccount != null;
    }

    private CreateTransactionRequest createTransactionRequest(BigDecimal amount, String senderName, String description){
        CreateTransactionRequest transactionRequest = new CreateTransactionRequest();
        transactionRequest.setAmount(amount);
        transactionRequest.setSenderName(senderName);
        transactionRequest.setDescription(description);

        return transactionRequest;

    }
}
