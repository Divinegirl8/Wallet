package com.wallet.keycloak.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wallet.keycloak.data.model.Transaction;
import com.wallet.keycloak.data.model.User;
import com.wallet.keycloak.data.model.WalletAccount;
import com.wallet.keycloak.dtos.request.BalanceIncrementRequest;
import com.wallet.keycloak.dtos.request.CreateWalletRequest;
import com.wallet.keycloak.dtos.request.FundWalletRequest;
import com.wallet.keycloak.dtos.response.ApiResponse;
import com.wallet.keycloak.dtos.response.FundWalletResponse;
import com.wallet.keycloak.dtos.response.TransactionNotFoundException;
import com.wallet.keycloak.exception.PhoneNumberExistException;
import com.wallet.keycloak.exception.WalletNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Slf4j
class WalletServiceTest {
    @Autowired
    private WalletService walletService;
    @Autowired
    private AuthenticationService authenticationService;


    public CreateWalletRequest createWalletRequest (String walletNumber, String fullName, String pin){
        CreateWalletRequest createWalletRequest = new CreateWalletRequest();
        createWalletRequest.setWalletNumber(walletNumber);
        createWalletRequest.setFullName(fullName);
        createWalletRequest.setPin(pin);

        return createWalletRequest;
    }

    public FundWalletRequest fundWalletRequest(BigDecimal amount, Long userId,String description,String senderName){
        FundWalletRequest fundWalletRequest = new FundWalletRequest();

        fundWalletRequest.setAmount(amount);
        fundWalletRequest.setUserId(userId);
        fundWalletRequest.setDescription(description);
        fundWalletRequest.setSenderName(senderName);

        return fundWalletRequest;
    }

   public BalanceIncrementRequest balanceIncrementRequest(String reference, Long transactionId){
       BalanceIncrementRequest balanceIncrementRequest = new BalanceIncrementRequest();
       balanceIncrementRequest.setReference(reference);
       balanceIncrementRequest.setTransactionId(transactionId);

       return balanceIncrementRequest;
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

    @Test void test_That_Wallet_Can_Be_Funded() throws JsonProcessingException, WalletNotFoundException {
        FundWalletRequest walletRequest = fundWalletRequest(BigDecimal.valueOf(12380),1L,"description","sednername");
      FundWalletResponse fundWalletResponse = walletService.fundWallet(walletRequest);
      assertThat(fundWalletResponse).isNotNull();
      log.info("{}->",fundWalletResponse);
    }
    @Test void test_That_User1_Wallet_Can_Be_Funded_Twice() throws JsonProcessingException, WalletNotFoundException {
        FundWalletRequest walletRequest = fundWalletRequest(BigDecimal.valueOf(10_000),1L,"description","sednername");
        FundWalletResponse fundWalletResponse = walletService.fundWallet(walletRequest);
        assertThat(fundWalletResponse).isNotNull();
        log.info("{}->",fundWalletResponse);
    }

    @Test void test_That_Wallet_Can_Be_Funded2() throws JsonProcessingException, WalletNotFoundException {
        FundWalletRequest walletRequest = fundWalletRequest(BigDecimal.valueOf(80000),3L,"description","sednername");
        FundWalletResponse fundWalletResponse = walletService.fundWallet(walletRequest);
        assertThat(fundWalletResponse).isNotNull();
        log.info("{}->",fundWalletResponse);
    }

    @Test void test_That_User3_Wallet_Can_Be_Funded_Twice() throws JsonProcessingException, WalletNotFoundException {
        FundWalletRequest walletRequest = fundWalletRequest(BigDecimal.valueOf(102_000),3L,"description","name");
        FundWalletResponse fundWalletResponse = walletService.fundWallet(walletRequest);
        assertThat(fundWalletResponse).isNotNull();
        log.info("{}->",fundWalletResponse);
    }

    @Test
    void testVerification(){
        String res = walletService.verifyPayment("nphm87xla6");
        log.info("res -> {}", res);

    }

    @Test void  test_Balance_Increment() throws WalletNotFoundException, TransactionNotFoundException {
        User user = authenticationService.findUserById(1L);
        BigDecimal balance = walletService.checkBalance(user.getWalletAccount().getId());
        log.info("balance before receiving money -> {}",balance);

        BalanceIncrementRequest balanceIncrementRequest = balanceIncrementRequest("bp22owvp47",102L);
      Transaction transaction = walletService.balanceIncrement(balanceIncrementRequest);
      log.info("res -> {}", transaction);
        BigDecimal balance2 = walletService.checkBalance(user.getWalletAccount().getId());
        log.info("balance after receiving money -> {}",balance2);
    }

    @Test void  test_Balance_Increment2() throws WalletNotFoundException, TransactionNotFoundException {
        User user = authenticationService.findUserById(3L);
        BigDecimal balance = walletService.checkBalance(user.getWalletAccount().getId());
        log.info("balance before receiving money -> {}",balance);

        BalanceIncrementRequest balanceIncrementRequest = balanceIncrementRequest("8v1gq3e5wx",202L);
        Transaction transaction = walletService.balanceIncrement(balanceIncrementRequest);
        log.info("res -> {}", transaction);
        BigDecimal balance2 = walletService.checkBalance(user.getWalletAccount().getId());
        log.info("balance after receiving money -> {}",balance2);
    }

}