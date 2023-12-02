package com.example.ComUniShare.repositories;

import com.example.ComUniShare.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, String> {
    UserDetails findByLogin(String login);

    User findUserByLogin(String name);
}
