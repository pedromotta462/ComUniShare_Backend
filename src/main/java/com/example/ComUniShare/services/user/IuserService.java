package com.example.ComUniShare.services.user;

import com.example.ComUniShare.domain.user.User;

import java.util.List;

public interface IuserService {
    User findById(String userId);

    User findUserByLogin(String login);

    User myData();

    List<User> findAllUsers();

    void saveUser(User user);

    void updateUser(User user);

    void deleteUser(String userId);
}
