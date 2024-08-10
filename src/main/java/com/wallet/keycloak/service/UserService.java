package com.wallet.keycloak.service;

import com.wallet.keycloak.dtos.request.RegisterRequest;
import com.wallet.keycloak.dtos.request.RegisterResponse;
import com.wallet.keycloak.exception.UsernameExistException;

public interface UserService {
    RegisterResponse userRegistration(RegisterRequest request) throws UsernameExistException;
}
