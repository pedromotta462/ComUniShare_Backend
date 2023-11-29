package com.example.ComUniShare.services.user;

import com.example.ComUniShare.domain.user.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface IuserService {
    User findById(String userId);

    User findUserByLogin(String login);

    User myData();

    List<User> findAllUsers();

    void saveUser(User user);

    void updateUser(User user);

    ResponseEntity<String> updateUserAdapted(String userId, Map<String, Object> updates);

    void deleteUser(String userId);
}
