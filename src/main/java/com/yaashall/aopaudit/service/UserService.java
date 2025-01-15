package com.yaashall.aopaudit.service;

import com.yaashall.aopaudit.annotation.Loggable;
import com.yaashall.aopaudit.entity.User;
import com.yaashall.aopaudit.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Yassine CHALH
 */

@Service
public class UserService {

    public final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String performAction(String username) {
        return "User " + username + " performed an action.";
    }

    @Loggable(action = "CREATE_USER")
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Loggable(action = "GET_ALL_USERS")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Loggable(action = "GET_USER_BY_ID")
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Loggable(action = "UPDATE_USER")
    public User updateUser(Long id, User userDetails) {
        return userRepository.findById(id).map(user -> {
            user.setUsername(userDetails.getUsername());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            return userRepository.save(user);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Loggable(action = "DELETE_USER")
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
