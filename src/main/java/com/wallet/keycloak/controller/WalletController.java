package com.wallet.keycloak.controller;

import com.wallet.keycloak.data.model.Transaction;
import com.wallet.keycloak.dtos.request.BalanceIncrementRequest;
import com.wallet.keycloak.dtos.request.FundWalletRequest;
import com.wallet.keycloak.dtos.response.FundWalletResponse;
import com.wallet.keycloak.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("api/v1/wallet")
@RequiredArgsConstructor
public class WalletController {
    private final WalletService walletService;

    @PostMapping("/fundWallet")
    @PreAuthorize("hasRole('client_user')")
    public ResponseEntity<?> fundWallet(@RequestBody FundWalletRequest request){
        try{
            FundWalletResponse response = walletService.fundWallet(request);
            return ResponseEntity.ok().body(response);
        }
        catch (Exception exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping("/incrementBalance")
    @PreAuthorize("hasRole('client_user')")
    public ResponseEntity<?> incrementBalance(@RequestBody BalanceIncrementRequest request){
        try{
            Transaction response = walletService.balanceIncrement(request);
            return ResponseEntity.ok().body(response);
        }
        catch (Exception exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @GetMapping("/checkBalance/{id}")
    @PreAuthorize("hasRole('client_user')")
    public ResponseEntity<?> checkBalance(@PathVariable("id") Long id){
        try{
            BigDecimal response = walletService.checkBalance(id);
            return ResponseEntity.ok().body(response);
        }
        catch (Exception exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }
}
