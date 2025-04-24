package com.gaulab.weshoot.controller;

import com.gaulab.weshoot.dto.UserRegistrationDTO;
import com.gaulab.weshoot.model.User;
import com.gaulab.weshoot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody UserRegistrationDTO user) {
        return ResponseEntity.ok(userService.register(user));
    }

}
