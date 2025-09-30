package com.ecommerce.libreriasaintpatrick.controller;

import com.ecommerce.libreriasaintpatrick.model.Cafe;
import com.ecommerce.libreriasaintpatrick.model.Libro;
import com.ecommerce.libreriasaintpatrick.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private ProductoService service;

    @PostMapping
    public Libro create(@RequestBody Libro libro) {
        return (Libro) service.save(libro);
    }

    @GetMapping
    public List<Libro> getAllLibros() {
        return service.findAllByTipo(Libro.class).stream()
                .filter(libro -> libro.getDemo() == null || Boolean.FALSE.equals(libro.getDemo()))
                .toList();
    }

    @GetMapping("/{id}")
    public Libro getLibroById(@PathVariable Long id) {
        return (Libro) service.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Libro> updateLibro(@PathVariable Long id, @RequestBody Libro libroActualizado) {
        Libro libroExistente = (Libro) service.findById(id);
        if (libroExistente == null) {
            return ResponseEntity.notFound().build();
        }

        // Actualizamos los campos relevantes
        libroExistente.setNombre(libroActualizado.getNombre());
        libroExistente.setDescripcion(libroActualizado.getDescripcion());
        libroExistente.setPrecio(libroActualizado.getPrecio());
        libroExistente.setStock(libroActualizado.getStock());
        libroExistente.setAutor(libroActualizado.getAutor());
        libroExistente.setEditorial(libroActualizado.getEditorial());
        libroExistente.setEstado(libroActualizado.getEstado());

        Libro libroGuardado = (Libro) service.save(libroExistente);
        return ResponseEntity.ok(libroGuardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
