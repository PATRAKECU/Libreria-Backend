package com.ecommerce.libreriasaintpatrick.service;

import com.ecommerce.libreriasaintpatrick.model.Usuario;
import com.ecommerce.libreriasaintpatrick.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Buscar usuario por email
    public Optional<Usuario> findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Verificar si el email ya está registrado
    public boolean existsByEmail(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    // Guardar nuevo usuario
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    // Buscar por ID
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    // Listar todos (solo si lo necesitas como admin)
    public Iterable<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    // Eliminar usuario (opcional)
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }
}