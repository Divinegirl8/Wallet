package com.wallet.keycloak.service.implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.keycloak.config.PayStackConfig;
import com.wallet.keycloak.dtos.response.ApiResponse;
import com.wallet.keycloak.dtos.response.InitializePaymentResponse;
import com.wallet.keycloak.service.PayStackService;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class PayStackServiceApp implements PayStackService {
    private final RestTemplate restTemplate;
    private final PayStackConfig payStackConfig;
    @Override
    public InitializePaymentResponse initializePayment(String email, BigDecimal amount) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + payStackConfig.getPayStackApiKey());
        headers.set("Content-Type", "application/json");

        String amountAsString = BigDecimal.valueOf(amount.longValue() * 100).toString();


        String requestBody = String.format("{\"email\":\"%s\", \"amount\":\"%s\"}", email, amountAsString);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);


        ResponseEntity<String> response = restTemplate.exchange(payStackConfig.getPayStackInitializerUrl(), HttpMethod.POST, entity, String.class);


        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response.getBody());

        JsonNode dataNode = rootNode.path("data");
        String authorizationUrl = dataNode.path("authorization_url").asText();
        String reference = dataNode.path("reference").asText();
        return new InitializePaymentResponse(authorizationUrl,reference);

    }

    @Override
    public ApiResponse<?> verifyPayment(String reference) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + payStackConfig.getPayStackApiKey());
        String url = payStackConfig.getVerificationUrl() + reference;

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, entity, ApiResponse.class).getBody();
    }
}
