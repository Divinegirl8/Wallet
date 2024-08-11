package com.wallet.keycloak.service;

import com.wallet.keycloak.dtos.request.LoginRequest;
import com.wallet.keycloak.dtos.request.RegisterRequest;
import com.wallet.keycloak.dtos.request.TokenRequest;
import com.wallet.keycloak.dtos.response.IntrospectionResponse;
import com.wallet.keycloak.dtos.response.LogoutResponse;
import com.wallet.keycloak.dtos.response.RegisterResponse;
import com.wallet.keycloak.dtos.response.LoginResponse;
import com.wallet.keycloak.exception.LoginCredentialException;
import com.wallet.keycloak.exception.PhoneNumberExistException;
import com.wallet.keycloak.exception.UsernameExistException;

public interface AuthenticationService {
    RegisterResponse userRegistration(RegisterRequest request) throws UsernameExistException, PhoneNumberExistException;
    LoginResponse login(LoginRequest loginRequest) throws LoginCredentialException;
    LogoutResponse logout(TokenRequest tokenRequest);
    IntrospectionResponse introspect(TokenRequest tokenRequest);
}
