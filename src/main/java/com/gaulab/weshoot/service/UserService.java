package com.gaulab.weshoot.service;

import com.gaulab.weshoot.dto.UserRegistrationDTO;
import com.gaulab.weshoot.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    // Additional methods specific to UserService can be defined here
    List<User> getAllUsers();
    User getUserById(Long id);
    User register(UserRegistrationDTO userDTO);
    User updateUser(Long id, User user);
    void updatePassword(Long id, String oldPassword, String newPassword);
    void deleteUser(Long id);
}
