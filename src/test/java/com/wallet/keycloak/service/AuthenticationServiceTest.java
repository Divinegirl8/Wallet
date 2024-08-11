package com.wallet.keycloak.service;

import com.wallet.keycloak.dtos.request.LoginRequest;
import com.wallet.keycloak.dtos.request.RegisterRequest;
import com.wallet.keycloak.dtos.response.LoginResponse;
import com.wallet.keycloak.dtos.response.RegisterResponse;
import com.wallet.keycloak.exception.LoginCredentialException;
import com.wallet.keycloak.exception.UsernameExistException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Slf4j
class AuthenticationServiceTest {
    @Autowired
    private AuthenticationService authenticationService;

    public RegisterRequest registerRequest(String username,String firstname,String lastname,String password,String emailAddress){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(username);
        registerRequest.setFirstname(firstname);
        registerRequest.setLastname(lastname);
        registerRequest.setPassword(password);
        registerRequest.setEmailAddress(emailAddress);

        return registerRequest;
    }

    public LoginRequest loginRequest(String username,String password){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username);
        loginRequest.setPassword(password);

        return loginRequest;
    }


    @Test
    public void test_That_A_User_Can_Register() throws UsernameExistException {
        RegisterRequest request = registerRequest("mandykay","mandy","kiss","password","password@gmail.com");
        RegisterResponse response = authenticationService.userRegistration(request);
        log.info("{}->",response);
        assertThat(response).isNotNull();
    }

    @Test
    public void test_That_A_User_Can_Login_If_Registered() throws LoginCredentialException {
        LoginRequest request = loginRequest("mandykay","password");
        LoginResponse response = authenticationService.login(request);
        log.info("{}->",response);
        assertThat(response).isNotNull();

    }

    @Test
    public void test_That_A_User_Cannot_Login_If_Username_Is_Not_Valid() throws LoginCredentialException {
        LoginRequest request = loginRequest("mandy","password");
        assertThrows(LoginCredentialException.class,()-> authenticationService.login(request));
    }

    @Test
    public void test_That_A_User_Cannot_Login_If_Password_Is_Not_Valid() throws LoginCredentialException {
        LoginRequest request = loginRequest("mandykay","word");
        assertThrows(LoginCredentialException.class,()-> authenticationService.login(request));
    }





}