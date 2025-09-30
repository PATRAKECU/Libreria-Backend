package com.ecommerce.libreriasaintpatrick.service;

import com.ecommerce.libreriasaintpatrick.model.Pedido;
import com.ecommerce.libreriasaintpatrick.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido save(Pedido p) { return pedidoRepository.save(p); }

    public List<Pedido> findByUsuario(Long uid) {
        return pedidoRepository.findByUsuarioId(uid);
    }
    // Eliminar pedido por ID
    public void delete(Long id) {
        if (!pedidoRepository.existsById(id)) {
            throw new RuntimeException("Pedido no encontrado");
        }
        pedidoRepository.deleteById(id);
    }

    // Actualizar pedido
    public Pedido update(Long id, Pedido nuevoPedido) {
        Pedido existente = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        // Actualizar campos relevantes
        existente.setFecha(nuevoPedido.getFecha());
        existente.setItems(nuevoPedido.getItems());
        // Puedes agregar más campos si los tienes

        return pedidoRepository.save(existente);
    }

    // Buscar por ID
    public Optional<Pedido> findById(Long id) {
        return pedidoRepository.findById(id); // ya usa el EntityGraph
    }
}
