package com.wallet.keycloak.service;

import com.wallet.keycloak.data.model.WalletAccount;
import com.wallet.keycloak.dtos.request.CreateWalletRequest;
import com.wallet.keycloak.exception.PhoneNumberExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class WalletServiceTest {
    @Autowired
    private WalletService walletService;

    public CreateWalletRequest createWalletRequest (String walletNumber, String fullName, String pin){
        CreateWalletRequest createWalletRequest = new CreateWalletRequest();
        createWalletRequest.setWalletNumber(walletNumber);
        createWalletRequest.setFullName(fullName);
        createWalletRequest.setPin(pin);

        return createWalletRequest;
    }

    @Test void test_That_Wallet_Can_Be_Created() throws PhoneNumberExistException {
        CreateWalletRequest walletRequest = createWalletRequest("09087654321","Firstname Lastname", "8989");

       WalletAccount wallet = walletService.createWalletAccount(walletRequest);
       assertThat(wallet).isNotNull();
    }

    @Test void test_That_Wallet_Cannot_Be_Created_With_Same_Number(){
        CreateWalletRequest walletRequest = createWalletRequest("09087654321","Firstname Lastname", "8989");

        assertThrows(PhoneNumberExistException.class, () ->walletService.createWalletAccount(walletRequest));
    }

}