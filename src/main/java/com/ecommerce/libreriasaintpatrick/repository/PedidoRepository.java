package com.ecommerce.libreriasaintpatrick.repository;

import com.ecommerce.libreriasaintpatrick.model.Pedido;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    @EntityGraph(attributePaths = {"items", "items.producto"})
    List<Pedido> findByUsuarioId(Long usuarioId);
    @EntityGraph(attributePaths = {"items", "items.producto"})
    Optional<Pedido> findById(Long id);
}

