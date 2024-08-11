package com.wallet.keycloak.service;

import com.wallet.keycloak.data.model.Transaction;
import com.wallet.keycloak.dtos.request.CreateTransactionRequest;
import com.wallet.keycloak.exception.WalletNotFoundException;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class TransactionServiceTest {
    @Autowired
    private TransactionService transactionService;
    CreateTransactionRequest transactionRequest(String description,String senderName, BigDecimal amount){
        CreateTransactionRequest createTransactionRequest = new CreateTransactionRequest();
        createTransactionRequest.setDescription(description);
        createTransactionRequest.setSenderName(senderName);
        createTransactionRequest.setAmount(amount);

        return createTransactionRequest;
    }

    @Test void testThatTransactionCanBeMade() throws WalletNotFoundException {
        CreateTransactionRequest createTransactionRequest = transactionRequest("description", "sender name",BigDecimal.valueOf(20_000));
        Transaction transaction = transactionService.createTransaction(createTransactionRequest,1L);
        log.info("{} ->",transaction);
        assertThat(transaction).isNotNull();
    }



}