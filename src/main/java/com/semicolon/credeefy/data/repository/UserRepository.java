package com.semicolon.credeefy.data.repository;

import com.semicolon.credeefy.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);
}
