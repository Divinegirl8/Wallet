package com.wallet.keycloak.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class PayStackConfig {
    @Value("${paystack.api.key}")
    private String payStackApiKey;
    @Value("${paystack.base.initialize.url}")
    private String payStackInitializerUrl;
    @Value("${paystack.verification_url}")
    private String verificationUrl;
}
