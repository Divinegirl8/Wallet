package com.wallet.keycloak.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wallet.keycloak.dtos.response.ApiResponse;
import com.wallet.keycloak.dtos.response.InitializePaymentResponse;

import java.math.BigDecimal;

public interface PayStackService {
    InitializePaymentResponse initializePayment(String email, BigDecimal amount) throws JsonProcessingException;
    ApiResponse<?> verifyPayment(String reference);
}
