package com.wallet.keycloak.service.implementation;

import com.wallet.keycloak.data.model.WalletAccount;
import com.wallet.keycloak.data.repository.WalletRepository;
import com.wallet.keycloak.dtos.request.CreateWalletRequest;
import com.wallet.keycloak.exception.PhoneNumberExistException;
import com.wallet.keycloak.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletServiceApp implements WalletService {
    private final WalletRepository walletRepository;
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

    private boolean walletAccountExist(String walletAccountNumber){
        WalletAccount walletAccount = walletRepository.findByWalletNumber(walletAccountNumber);

        return walletAccount != null;
    }
}
