package com.ecommerce.libreriasaintpatrick.controller;

import com.ecommerce.libreriasaintpatrick.model.Libro;
import com.ecommerce.libreriasaintpatrick.model.Separador;
import com.ecommerce.libreriasaintpatrick.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/separadores")
public class SeparadorController {

    @Autowired
    private ProductoService service;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Separador create(@RequestBody Separador separador) {
        return (Separador) service.save(separador);
    }

    @GetMapping
    public List<Separador> getAllSeparadores() {
        return service.findAllByTipo(Separador.class).stream()
                .filter(separador -> separador.getDemo() == null || Boolean.FALSE.equals(separador.getDemo()))
                .toList();
    }

    @GetMapping("/{id}")
    public Separador getSeparadorById(@PathVariable Long id) {
        return (Separador) service.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Separador> updateSeparador(@PathVariable Long id, @RequestBody Separador separadorActualizado) {
        Separador separadorExistente = (Separador) service.findById(id);
        if (separadorExistente == null) {
            return ResponseEntity.notFound().build();
        }

        separadorExistente.setNombre(separadorActualizado.getNombre());
        separadorExistente.setDescripcion(separadorActualizado.getDescripcion());
        separadorExistente.setPrecio(separadorActualizado.getPrecio());
        separadorExistente.setStock(separadorActualizado.getStock());
        separadorExistente.setMaterial(separadorActualizado.getMaterial());
        separadorExistente.setDimensionesCm(separadorActualizado.getDimensionesCm());

        Separador separadorGuardado = (Separador) service.save(separadorExistente);
        return ResponseEntity.ok(separadorGuardado);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeparador(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
