package com.ecommerce.libreriasaintpatrick.repository;

import com.ecommerce.libreriasaintpatrick.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Buscar usuario por email (para login)
    Optional<Usuario> findByEmail(String email);

    // Verificar si ya existe un email registrado (para registro)
    boolean existsByEmail(String email);
}
