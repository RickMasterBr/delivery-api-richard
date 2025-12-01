package com.deliverytech.delivery.controller; //

import com.deliverytech.delivery.dto.auth.AuthRequest;
import com.deliverytech.delivery.dto.auth.AuthResponse;
import com.deliverytech.delivery.dto.auth.RegisterRequest;
import com.deliverytech.delivery.entity.Usuario;
import com.deliverytech.delivery.repository.UsuarioRepository;
import com.deliverytech.delivery.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email já existe");
        }

        var usuario = Usuario.builder()
                .nome(request.nome())
                .email(request.email())
                .senha(passwordEncoder.encode(request.senha()))
                .role(request.role())
                .ativo(true)
                .dataCriacao(LocalDateTime.now())
                .restauranteId(request.restauranteId())
                .build();

        usuarioRepository.save(usuario);
        var token = jwtUtil.generateToken(usuario, usuario);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.senha())
        );
        var usuario = usuarioRepository.findByEmail(request.email()).orElseThrow();
        var token = jwtUtil.generateToken(usuario, usuario);
        return ResponseEntity.ok(new AuthResponse(token));
    }
}