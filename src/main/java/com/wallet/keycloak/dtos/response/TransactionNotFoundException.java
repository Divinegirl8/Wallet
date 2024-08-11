package com.wallet.keycloak.dtos.response;

public class TransactionNotFoundException extends Exception{
    public TransactionNotFoundException(String message){
        super(message);
    }
}
