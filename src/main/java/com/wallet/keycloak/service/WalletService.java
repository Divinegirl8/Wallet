package com.wallet.keycloak.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wallet.keycloak.data.model.Transaction;
import com.wallet.keycloak.data.model.WalletAccount;
import com.wallet.keycloak.dtos.request.BalanceIncrementRequest;
import com.wallet.keycloak.dtos.request.CreateWalletRequest;
import com.wallet.keycloak.dtos.request.FundWalletRequest;
import com.wallet.keycloak.dtos.response.ApiResponse;
import com.wallet.keycloak.dtos.response.FundWalletResponse;
import com.wallet.keycloak.dtos.response.TransactionNotFoundException;
import com.wallet.keycloak.exception.PhoneNumberExistException;
import com.wallet.keycloak.exception.WalletNotFoundException;

import java.math.BigDecimal;

public interface WalletService {
    WalletAccount createWalletAccount(CreateWalletRequest createWalletRequest) throws PhoneNumberExistException;
    WalletAccount findWalletById(Long id) throws WalletNotFoundException;
    FundWalletResponse fundWallet(FundWalletRequest request) throws JsonProcessingException, WalletNotFoundException;

    String verifyPayment(String reference);

    Transaction balanceIncrement(BalanceIncrementRequest balanceIncrementRequest) throws TransactionNotFoundException, WalletNotFoundException;

    BigDecimal checkBalance(Long walletId) throws WalletNotFoundException;
}
