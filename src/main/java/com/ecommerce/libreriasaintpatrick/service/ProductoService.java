package com.ecommerce.libreriasaintpatrick.service;

import com.ecommerce.libreriasaintpatrick.model.Producto;
import com.ecommerce.libreriasaintpatrick.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository repo;

    public List<Producto> findAll() {
        return repo.findAll();
    }

    public Producto findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe producto con id " + id));
    }

    public Producto save(Producto producto) {
        return repo.save(producto);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public <T extends Producto> List<T> findAllByTipo(Class<T> tipo) {
        return repo.findAll().stream()
                .filter(tipo::isInstance)
                .map(tipo::cast)
                .toList();
    }
    public List<Producto> listarProductosReales() {
        return repo.findByDemoFalse();
    }
}

