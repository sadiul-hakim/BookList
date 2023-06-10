package com.hakimbooks.hakimbooks.controller;

import com.hakimbooks.hakimbooks.pojo.*;
import com.hakimbooks.hakimbooks.service.AuthService;
import com.hakimbooks.hakimbooks.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequestData authRequestData,
                                              HttpServletResponse response){
        AuthResponse authResponse = authService.login(authRequestData);
        Cookie cookie=new Cookie("user-token",authResponse.getToken());
        response.addCookie(cookie);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody UserRequestData userRequestData) {
        UserResponse response = userService.register(userRequestData);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestBody AuthResponse authResponse) {
        UserResponse response = userService.validateToken(authResponse);
        if(response == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ApiResponse("Invalid Token!",true)
        );
        return ResponseEntity.ok(response);
    }
}