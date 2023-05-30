package com.hakimbooks.hakimbooks.controller;

import com.hakimbooks.hakimbooks.pojo.AuthResponse;
import com.hakimbooks.hakimbooks.pojo.AuthRequestData;
import com.hakimbooks.hakimbooks.pojo.UserRequestData;
import com.hakimbooks.hakimbooks.pojo.UserResponse;
import com.hakimbooks.hakimbooks.service.AuthService;
import com.hakimbooks.hakimbooks.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequestData authRequestData){
        AuthResponse authResponse = authService.login(authRequestData);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequestData userRequestData) {
        UserResponse response = userService.register(userRequestData);
        return ResponseEntity.ok(response);
    }
}
