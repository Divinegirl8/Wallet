package com.wallet.keycloak.controller;

import com.wallet.keycloak.dtos.request.RegisterRequest;
import com.wallet.keycloak.dtos.request.RegisterResponse;
import com.wallet.keycloak.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/user/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("registration")
    public ResponseEntity<?> userRegistration(@RequestBody RegisterRequest registerRequest){
        try {
            RegisterResponse response = userService.userRegistration(registerRequest);
            return ResponseEntity.ok().body(response);

        }catch (Exception exception){
            return ResponseEntity.badRequest().body(exception.getMessage());
        }
    }



}
