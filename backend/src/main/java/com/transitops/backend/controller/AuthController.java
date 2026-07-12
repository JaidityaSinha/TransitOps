package com.transitops.backend.controller;

import com.transitops.backend.dto.auth.AuthRequest;
import com.transitops.backend.dto.auth.AuthResponse;
import com.transitops.backend.dto.auth.RegisterRequest;
import com.transitops.backend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(
            @RequestBody RegisterRequest request) {

        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody AuthRequest request) {

        return authService.login(request);
    }
}