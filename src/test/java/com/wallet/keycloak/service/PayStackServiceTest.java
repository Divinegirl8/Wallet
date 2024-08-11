package com.wallet.keycloak.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wallet.keycloak.dtos.response.InitializePaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
class PayStackServiceTest {
    @Autowired
    private PayStackService payStackService;


    @Test void test_That_Payment_Can_Be_Received() throws JsonProcessingException {

        InitializePaymentResponse response = payStackService.initializePayment("test@gmail.com",BigDecimal.valueOf(105_560));
        log.info("{} ->",response);
        assertThat(response).isNotNull();
    }



}