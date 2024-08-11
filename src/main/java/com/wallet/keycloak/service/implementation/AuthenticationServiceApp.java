package com.wallet.keycloak.service.implementation;

import com.wallet.keycloak.data.model.User;
import com.wallet.keycloak.data.model.WalletAccount;
import com.wallet.keycloak.data.repository.UserRepository;
import com.wallet.keycloak.dtos.request.CreateWalletRequest;
import com.wallet.keycloak.dtos.request.LoginRequest;
import com.wallet.keycloak.dtos.request.RegisterRequest;
import com.wallet.keycloak.dtos.request.TokenRequest;
import com.wallet.keycloak.dtos.response.IntrospectionResponse;
import com.wallet.keycloak.dtos.response.LoginResponse;
import com.wallet.keycloak.dtos.response.LogoutResponse;
import com.wallet.keycloak.dtos.response.RegisterResponse;
import com.wallet.keycloak.exception.LoginCredentialException;
import com.wallet.keycloak.exception.PhoneNumberExistException;
import com.wallet.keycloak.exception.UsernameExistException;
import com.wallet.keycloak.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceApp implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Value("${spring.security.oauth2.authorizationserver.endpoint.token-uri}")
    private String url;
    @Value("${keycloak.client-id}")
    private String clientId;
    @Value("${keycloak.grant-type}")
    private String grantType;
    @Value("${keycloak.username}")
    private String username;
    @Value("${keycloak.password}")
    private String password;
    @Value("${keycloak.logout_url}")
    private String logoutUrl;
    @Value("${keycloak.introspection_endpoint}")
    private String introspectionEndpoint;
    private final WalletServiceApp walletServiceApp;



    @Override
    public RegisterResponse userRegistration(RegisterRequest request) throws UsernameExistException, PhoneNumberExistException {

        if (usernameExist(request.getUsername())){
            throw new UsernameExistException("Username already exists, please choose another username.");
        }
        CreateWalletRequest createWalletRequest = walletRequest(request.getPhoneNumber(),request.getFirstname(),request.getLastname(),request.getWalletPin());

        WalletAccount walletAccount = walletServiceApp.createWalletAccount(createWalletRequest);

        var user = User.builder()
                .username(request.getUsername())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(passwordEncoder.encode(request.getPassword()))
                .emailAddress(request.getEmailAddress())
                .phoneNumber(request.getPhoneNumber())
                .walletPin(request.getWalletPin())
                .walletAccount(walletAccount)
                .build();
        userRepository.save(user);

        String accessTokenValue = accessToken();
        String refreshTokenValue = refreshToken();
        String accountNumber = user.getWalletAccount().getWalletNumber();


        return new RegisterResponse("User registration successful",accountNumber,accessTokenValue,refreshTokenValue,user.getId().toString());
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws LoginCredentialException {
        User user = userRepository.findByUsername(loginRequest.getUsername());

        if (user == null){
            throw new LoginCredentialException("login credential is invalid");
        }

        boolean password = passwordEncoder.matches(loginRequest.getPassword(),user.getPassword());

        if (!password){
            throw new LoginCredentialException("login credential is invalid");
        }


        String accessTokenValue = accessToken();
        String refreshTokenValue = refreshToken();



        return new LoginResponse("login successful",accessTokenValue,refreshTokenValue);
    }

    @Override
    public LogoutResponse logout(TokenRequest tokenRequest) {
     logoutUser(tokenRequest);

     LogoutResponse logoutResponse = new LogoutResponse();
     logoutResponse.setMessage("logged out");

        return logoutResponse;
    }

    @Override
    public IntrospectionResponse introspect(TokenRequest tokenRequest) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = realmDetails(tokenRequest);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        Map responseEntity = restTemplate.exchange(introspectionEndpoint, HttpMethod.POST, entity, Map.class).getBody();
        return new IntrospectionResponse(responseEntity);
    }

    private Map getToken() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("username", username);
        map.add("password", password);
        map.add("grant_type", grantType);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        ResponseEntity<Map> responseEntity = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);
        return responseEntity.getBody();
    }

    private void logoutUser(TokenRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = realmDetails(request);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        restTemplate.exchange(logoutUrl, HttpMethod.POST, entity, Map.class);

    }

    private MultiValueMap<String, String> realmDetails(TokenRequest request) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", clientId);
        map.add("refresh_token", request.getToken());
        return map;
    }

    private String accessToken(){
        Map responseMap = getToken();
        return responseMap.get("access_token").toString();
    }

    private String refreshToken(){
        Map responseMap = getToken();
        return responseMap.get("refresh_token").toString();
    }

    private boolean usernameExist(String username){
        User user = userRepository.findByUsername(username);

        return user != null;
    }

    private CreateWalletRequest walletRequest(String accountNumber,String firstName, String lastName,String pin){
        CreateWalletRequest createWalletRequest = new CreateWalletRequest();

        createWalletRequest.setWalletNumber(accountNumber);
        createWalletRequest.setFullName(firstName + " " + lastName);
        createWalletRequest.setPin(pin);

        return createWalletRequest;
    }
}
