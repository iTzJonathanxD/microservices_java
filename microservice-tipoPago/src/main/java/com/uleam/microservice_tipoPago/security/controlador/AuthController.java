package com.uleam.microservice_tipoPago.security.controlador;

import com.uleam.microservice_tipoPago.security.modelos.AuthResponse;
import com.uleam.microservice_tipoPago.security.modelos.LoginRequest;
import com.uleam.microservice_tipoPago.security.modelos.RegisterRequest;
import com.uleam.microservice_tipoPago.security.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = {"http://localhost:8080"})
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request)
    {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(value = "register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(authService.register(request));
    }
}
