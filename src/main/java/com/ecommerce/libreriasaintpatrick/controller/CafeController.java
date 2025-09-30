package com.ecommerce.libreriasaintpatrick.controller;

import com.ecommerce.libreriasaintpatrick.model.Cafe;
import com.ecommerce.libreriasaintpatrick.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cafes")
public class CafeController {

    @Autowired
    private ProductoService service;

    @PostMapping
    public Cafe create(@RequestBody Cafe cafe) {
        return (Cafe) service.save(cafe);
    }

    @GetMapping
    public List<Cafe> getAllCafes() {
        return service.findAllByTipo(Cafe.class).stream()
                .filter(cafe -> cafe.getDemo() == null || Boolean.FALSE.equals(cafe.getDemo()))
                .toList();
    }

    @GetMapping("/{id}")
    public Cafe getCafeById(@PathVariable Long id) {
        return (Cafe) service.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cafe> updateCafe(@PathVariable Long id, @RequestBody Cafe cafeActualizado) {
        Cafe cafeExistente = (Cafe) service.findById(id);
        if (cafeExistente == null) {
            return ResponseEntity.notFound().build();
        }

        cafeExistente.setNombre(cafeActualizado.getNombre());
        cafeExistente.setDescripcion(cafeActualizado.getDescripcion());
        cafeExistente.setPrecio(cafeActualizado.getPrecio());
        cafeExistente.setStock(cafeActualizado.getStock());
        cafeExistente.setOrigen(cafeActualizado.getOrigen());
        cafeExistente.setPresentacion(cafeActualizado.getPresentacion());
        cafeExistente.setPesoGramos(cafeActualizado.getPesoGramos());

        Cafe cafeGuardado = (Cafe) service.save(cafeExistente);
        return ResponseEntity.ok(cafeGuardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCafe(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
