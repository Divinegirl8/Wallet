package com.wallet.keycloak.dtos.response;

import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class InitializePaymentResponse {
    private String url;
    private String reference;
}
