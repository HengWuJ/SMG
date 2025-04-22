package com.smg.user.service.impl;

import com.smg.user.service.UserService;
import com.smg.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.smg.pojo.User;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        try {
            logger.info("Fetching all users.");
            return userMapper.findAll();
        } catch (Exception e) {
            logger.error("Error fetching all users: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch users", e);
        }
    }

    @Override
    public Optional<User> getUserById(Integer id) {
        if (id == null || id <= 0) {
            logger.warn("Invalid user ID provided.");
            return Optional.empty();
        }
        try {
            logger.info("Fetching user by ID: {}", id);
            User user = userMapper.findById(id);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            logger.error("Error fetching user by ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to fetch user by ID", e);
        }
    }

    @Override
    public void createUser(User user) {
        if (user == null) {
            logger.warn("Attempt to create a null user.");
            return;
        }
        if(user.getFaceToken()!=null){
            logger.info("Creating new user: {}", user.getUsername());
            userMapper.insertWithToken(user);
        }
        else {
            try {
                logger.info("Creating new user: {}", user.getUsername());
                userMapper.insert(user);
            } catch (Exception e) {
                logger.error("Error creating user: {}", e.getMessage(), e);
                throw new RuntimeException("Failed to create user", e);
            }
        }
    }

    @Override
    public void updateUser(User user) {
        if (user == null || user.getId() == null || user.getId() <= 0) {
            logger.warn("Invalid update request for user: {}", user);
            return;
        }
        try {
            logger.info("Updating user with ID: {}", user.getId());
            userMapper.update(user);
        } catch (Exception e) {
            logger.error("Error updating user with ID {}: {}", user.getId(), e.getMessage(), e);
            throw new RuntimeException("Failed to update user", e);
        }
    }

    @Override
    public void deleteUser(Integer id) {
        if (id == null || id <= 0) {
            logger.warn("Invalid user ID provided for deletion.");
            return;
        }
        try {
            logger.info("Deleting user with ID: {}", id);
            userMapper.deleteById(id);
        } catch (Exception e) {
            logger.error("Error deleting user with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to delete user", e);
        }
    }

    @Override
    public void activateUser(Integer id) {
        if (id == null || id <= 0) {
            logger.warn("Invalid user ID provided for activation.");
            return;
        }
        try {
            logger.info("Activating user with ID: {}", id);
            userMapper.changeStatus(id, "ACTIVE");
        } catch (Exception e) {
            logger.error("Error activating user with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to activate user", e);
        }
    }

    @Override
    public void deactivateUser(Integer id) {
        if (id == null || id <= 0) {
            logger.warn("Invalid user ID provided for deactivation.");
            return;
        }
        try {
            logger.info("Deactivating user with ID: {}", id);
            userMapper.changeStatus(id, "INACTIVE");
        } catch (Exception e) {
            logger.error("Error deactivating user with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to deactivate user", e);
        }
    }

    @Override
    public void suspendUser(Integer id) {
        if (id == null || id <= 0) {
            logger.warn("Invalid user ID provided for suspension.");
            return;
        }
        try {
            logger.info("Suspending user with ID: {}", id);
            userMapper.changeStatus(id, "SUSPENDED");
        } catch (Exception e) {
            logger.error("Error suspending user with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to suspend user", e);
        }
    }

    @Override
    public void resetUserPassword(Integer id, String newPassword) {
        if (id == null || id <= 0 || newPassword == null || newPassword.isEmpty()) {
            logger.warn("Invalid parameters for resetting password.");
            return;
        }
        Optional<User> optionalUser = this.getUserById(id);
        User user = optionalUser.get();
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        try {
            logger.info("Resetting password for user with ID: {}", id);
            userMapper.resetPassword(id, newPassword);
        } catch (Exception e) {
            logger.error("Error resetting password for user with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to reset password", e);
        }
    }

    @Override
    public Optional<User> login(String username, String password) {
        if (username == null || password == null) {
            logger.warn("Invalid login attempt with null username or password.");
            return Optional.empty();
        }
        try {
            logger.info("Login attempt for user: {}", username);
            User user = userMapper.findByUsername(username);
//            if (user != null && passwordEncoder.matches(password, user.getPassword())) {
              if (user != null && password.equals(user.getPassword())) {
                return Optional.of(user);
            } else {
                logger.warn("Login failed for user: {}", username);
                return Optional.empty();
            }
        } catch (Exception e) {
            logger.error("Error during login for user {}: {}", username, e.getMessage(), e);
            throw new RuntimeException("Failed to login", e);
        }
    }

    public User findByUsername(String username) {
        if (username == null) {
            logger.warn("Invalid findByUsername attempt with null username.");
            return null;
        }
        try {
            logger.info("findByUsername attempt for user: {}", username);
            User user = userMapper.findByUsername(username);
            return user;
        } catch (Exception e) {
            logger.error("Error during findByUsername for user {}: {}", username, e.getMessage(), e);
            throw new RuntimeException("Failed to findByUsername", e);
        }
    }
}
