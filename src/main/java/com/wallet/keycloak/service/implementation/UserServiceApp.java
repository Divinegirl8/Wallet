package com.wallet.keycloak.service.implementation;

import com.wallet.keycloak.data.model.User;
import com.wallet.keycloak.data.repository.UserRepository;
import com.wallet.keycloak.dtos.request.RegisterRequest;
import com.wallet.keycloak.dtos.request.RegisterResponse;
import com.wallet.keycloak.exception.UsernameExistException;
import com.wallet.keycloak.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceApp implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RegisterResponse userRegistration(RegisterRequest request) throws UsernameExistException {

        if (usernameExist(request.getUsername())){
            throw new UsernameExistException("username already exist, please choose another username");
        }
        var user = User.builder()
                .username(request.getUsername())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .password(passwordEncoder.encode(request.getPassword()))
                .emailAddress(request.getEmailAddress())
                .build();
        userRepository.save(user);

        RegisterResponse response = new RegisterResponse();
        response.setMessage(user.getId().toString());
        return response;
    }

    private boolean usernameExist(String username){
        User user = userRepository.findByUsername(username);

        return user != null;
    }
}
