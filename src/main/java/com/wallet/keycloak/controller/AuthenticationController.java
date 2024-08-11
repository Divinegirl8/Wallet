package com.wallet.keycloak.controller;

import com.wallet.keycloak.dtos.request.LoginRequest;
import com.wallet.keycloak.dtos.request.RegisterRequest;
import com.wallet.keycloak.dtos.request.TokenRequest;
import com.wallet.keycloak.dtos.response.IntrospectionResponse;
import com.wallet.keycloak.dtos.response.LoginResponse;
import com.wallet.keycloak.dtos.response.LogoutResponse;
import com.wallet.keycloak.dtos.response.RegisterResponse;
import com.wallet.keycloak.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("registration")
    public ResponseEntity<?> registration(@RequestBody RegisterRequest registerRequest){
        try {
            RegisterResponse response = authenticationService.userRegistration(registerRequest);
            return ResponseEntity.ok().body(response);

        }catch (Exception exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try {
            LoginResponse response = authenticationService.login(loginRequest);
            return ResponseEntity.ok().body(response);

        }catch (Exception exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }


    @PostMapping("logout")
    public ResponseEntity<?> logout(@RequestBody TokenRequest tokenRequest){
        try {
            LogoutResponse response = authenticationService.logout(tokenRequest);
            return ResponseEntity.ok().body(response);

        }catch (Exception exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }

    @PostMapping("introspect")
    public ResponseEntity<?> introspectToken(@RequestBody TokenRequest tokenRequest){
        try {
            IntrospectionResponse response = authenticationService.introspect(tokenRequest);
            return ResponseEntity.ok().body(response);

        }catch (Exception exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }


}
