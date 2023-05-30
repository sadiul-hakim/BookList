package com.hakimbooks.hakimbooks.service;

import com.hakimbooks.hakimbooks.exception.ResourceNotFoundException;
import com.hakimbooks.hakimbooks.model.User;
import com.hakimbooks.hakimbooks.pojo.AuthResponse;
import com.hakimbooks.hakimbooks.pojo.AuthRequestData;
import com.hakimbooks.hakimbooks.repository.UserRepository;
import com.hakimbooks.hakimbooks.utility.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtHelper jwtHelper;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    public AuthResponse login(AuthRequestData authRequest){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword())
        );
        User user = userRepository.findByEmail(authRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email : " + authRequest.getEmail()));

        String token = jwtHelper.generateToken(user);
        return new AuthResponse(token);
    }
}
