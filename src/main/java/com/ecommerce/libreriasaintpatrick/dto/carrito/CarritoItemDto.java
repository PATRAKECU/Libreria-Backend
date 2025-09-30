package com.ecommerce.libreriasaintpatrick.dto.carrito;

import com.ecommerce.libreriasaintpatrick.dto.pedido.ItemConProducto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CarritoItemDto implements ItemConProducto {
    private Long productoId;
    private String nombre;
    private BigDecimal precioUnitario;
    private Integer cantidad;
}