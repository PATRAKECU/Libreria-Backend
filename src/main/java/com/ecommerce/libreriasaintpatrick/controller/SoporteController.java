package com.ecommerce.libreriasaintpatrick.controller;

import com.ecommerce.libreriasaintpatrick.model.Separador;
import com.ecommerce.libreriasaintpatrick.model.Soporte;
import com.ecommerce.libreriasaintpatrick.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/soportes")
public class SoporteController {

    @Autowired
    private ProductoService service;

    @PostMapping
    public Soporte create(@RequestBody Soporte soporte) {
        return (Soporte) service.save(soporte);
    }

    @GetMapping
    public List<Soporte> getAllSoportes() {
        return service.findAllByTipo(Soporte.class).stream()
                .filter(soporte -> soporte.getDemo() == null || Boolean.FALSE.equals(soporte.getDemo()))
                .toList();
    }

    @GetMapping("/{id}")
    public Soporte getSoporteById(@PathVariable Long id) {
        return (Soporte) service.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Soporte> updateSoporte(@PathVariable Long id, @RequestBody Soporte soporteActualizado) {
        Soporte soporteExistente = (Soporte) service.findById(id);
        if (soporteExistente == null) {
            return ResponseEntity.notFound().build();
        }

        soporteExistente.setNombre(soporteActualizado.getNombre());
        soporteExistente.setDescripcion(soporteActualizado.getDescripcion());
        soporteExistente.setPrecio(soporteActualizado.getPrecio());
        soporteExistente.setStock(soporteActualizado.getStock());
        soporteExistente.setMaterial(soporteActualizado.getMaterial());
        soporteExistente.setPeso(soporteActualizado.getPeso());

        Soporte soporteGuardado = (Soporte) service.save(soporteExistente);
        return ResponseEntity.ok(soporteGuardado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSoporte(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
