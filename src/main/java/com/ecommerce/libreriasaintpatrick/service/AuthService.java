package com.ecommerce.libreriasaintpatrick.service;

import com.ecommerce.libreriasaintpatrick.dto.auth.LoginDto;
import com.ecommerce.libreriasaintpatrick.dto.auth.RegisterDto;
import com.ecommerce.libreriasaintpatrick.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Service
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UsuarioService usuarioService;

    public AuthService(PasswordEncoder passwordEncoder, UsuarioService usuarioService) {
        this.passwordEncoder = passwordEncoder;
        this.usuarioService = usuarioService;
    }

    @Value("${jwt.secret}") private String jwtSecret;

    public void register(RegisterDto dto) {
        if (usuarioService.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Email ya registrado");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setRol("USER");
        usuarioService.save(usuario);
    }

    public String loginAndGetToken(LoginDto dto) {
        Usuario usuario = usuarioService.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!passwordEncoder.matches(dto.getPassword(), usuario.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        return generarToken(usuario); // ✅ ahora sí se reconoce
    }
    private String generarToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getEmail())
                .claim("roles", List.of(usuario.getRol()))
                .setIssuedAt(new Date())
                .setExpiration(Date.from(Instant.now().plus(2, ChronoUnit.HOURS)))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .compact();
    }
}
