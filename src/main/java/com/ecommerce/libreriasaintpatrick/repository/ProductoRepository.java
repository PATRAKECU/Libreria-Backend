package com.ecommerce.libreriasaintpatrick.repository;

import com.ecommerce.libreriasaintpatrick.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByDemoFalse(); // ✅ solo productos reales
}

