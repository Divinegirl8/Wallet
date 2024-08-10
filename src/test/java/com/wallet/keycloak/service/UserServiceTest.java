package com.wallet.keycloak.service;

import com.wallet.keycloak.dtos.request.RegisterRequest;
import com.wallet.keycloak.dtos.request.RegisterResponse;
import com.wallet.keycloak.exception.UsernameExistException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    public RegisterRequest registerRequest(String username,String firstname,String lastname,String password,String emailAddress){
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setUsername(username);
        registerRequest.setFirstname(firstname);
        registerRequest.setLastname(lastname);
        registerRequest.setPassword(password);
        registerRequest.setEmailAddress(emailAddress);

        return registerRequest;
    }

    @Test
    public void testThatAUserCanRegister() throws UsernameExistException {
        RegisterRequest request = registerRequest("mandykiss","mandy","kiss","password","password@gmail.com");
        RegisterResponse response = userService.userRegistration(request);
        assertThat(response).isNotNull();
    }

}