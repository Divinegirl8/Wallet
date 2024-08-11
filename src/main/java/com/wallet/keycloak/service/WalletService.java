package com.wallet.keycloak.service;

import com.wallet.keycloak.data.model.WalletAccount;
import com.wallet.keycloak.dtos.request.CreateWalletRequest;
import com.wallet.keycloak.exception.PhoneNumberExistException;

public interface WalletService {
    WalletAccount createWalletAccount(CreateWalletRequest createWalletRequest) throws PhoneNumberExistException;
}
