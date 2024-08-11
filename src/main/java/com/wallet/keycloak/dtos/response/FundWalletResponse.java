package com.wallet.keycloak.dtos.response;

import com.wallet.keycloak.data.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@ToString
public class FundWalletResponse {
   private Transaction transaction;
   private String reference;
   private String paymentUrl;
}
