package com.wallet.keycloak.data.repository;

import com.wallet.keycloak.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);
}
