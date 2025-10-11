package com.ecommerce.libreriasaintpatrick.dto.producto;

import com.ecommerce.libreriasaintpatrick.model.Producto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductoGeminiDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;

    public ProductoGeminiDto(Producto p) {
        this.id = p.getId();
        this.nombre = p.getNombre();
        this.descripcion = p.getDescripcion();
        this.precio = p.getPrecio();
    }
}
