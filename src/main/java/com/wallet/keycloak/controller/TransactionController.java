package com.wallet.keycloak.controller;

import com.wallet.keycloak.data.model.Transaction;
import com.wallet.keycloak.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/transaction")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/allTransaction")
    @PreAuthorize("hasRole('client_user')")
    public ResponseEntity<?> getTransactions(){
        try{
            List<Transaction> transactions = transactionService.findAllTransactions();
            return ResponseEntity.ok().body(transactions);
        }catch (Exception exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
