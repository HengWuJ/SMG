package com.smg.user.service;

import com.smg.pojo.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    Optional<User> getUserById(Integer id);

    void createUser(User user);

    void updateUser(User user);

    void deleteUser(Integer id);

    void activateUser(Integer id);

    void deactivateUser(Integer id);

    void suspendUser(Integer id);

    void resetUserPassword(Integer id, String newPassword);

    Optional<User> login(String username, String password);

    User findByUsername(String username);
}
