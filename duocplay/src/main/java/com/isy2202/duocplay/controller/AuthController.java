package com.isy2202.duocplay.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.isy2202.duocplay.dto.request.AuthRequestDTO;
import com.isy2202.duocplay.dto.response.AuthResponseDTO;
import com.isy2202.duocplay.security.JwtService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping("/api/auth")
public class AuthController {
    
    private final AuthenticationManager _authManager;
    private final JwtService _jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        _authManager = authenticationManager;
        _jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO request) {
        try {
            Authentication authentication = _authManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));                

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String token = _jwtService.generateToken(userDetails);

            String rol = userDetails.getAuthorities()
                .iterator()
                .next()
                .getAuthority()
                .replaceFirst("^ROLE_", "");

            AuthResponseDTO response = AuthResponseDTO.builder()
                .accessToken(token)
                .tokenType("Bearer")
                .username(userDetails.getUsername())
                .rol(rol)
                .build();

            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Usuario o contraseñas incorrectos");
        }
        
    }

}
