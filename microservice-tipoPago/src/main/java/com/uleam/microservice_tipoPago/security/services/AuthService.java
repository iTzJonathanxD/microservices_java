package com.uleam.microservice_tipoPago.security.services;

import com.uleam.microservice_tipoPago.security.JwtService;
import com.uleam.microservice_tipoPago.security.modelos.AuthResponse;
import com.uleam.microservice_tipoPago.security.modelos.LoginRequest;
import com.uleam.microservice_tipoPago.security.modelos.RegisterRequest;
import com.uleam.microservice_tipoPago.security.modelos.User.Role;
import com.uleam.microservice_tipoPago.security.modelos.User.User;
import com.uleam.microservice_tipoPago.security.modelos.User.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    // Constructor explícito para inyección de dependencias
    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        UserDetails user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String token = jwtService.getToken(user);

        return new AuthResponse(token);
    }

    public AuthResponse register(RegisterRequest request) {
        User user = new User(
                null,
                request.getUsername(),
                request.getFirstname(),
                passwordEncoder.encode(request.getPassword()),
                Role.USER
        );

        userRepository.save(user);

        String token = jwtService.getToken(user);

        return new AuthResponse(token);
    }

    private void createDefaultUserIfNotExist() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User defaultUser = new User(
                    null, // ID será generado automáticamente
                    "admin",
                    "Administrador",
                    passwordEncoder.encode("admin"),
                    Role.USER
            );

            userRepository.save(defaultUser);
        }
    }
}
