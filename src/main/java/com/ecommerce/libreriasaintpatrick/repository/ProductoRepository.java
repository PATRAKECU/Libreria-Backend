package com.ecommerce.libreriasaintpatrick.repository;

import com.ecommerce.libreriasaintpatrick.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductoRepository extends JpaRepository<Producto, Long> {
}

