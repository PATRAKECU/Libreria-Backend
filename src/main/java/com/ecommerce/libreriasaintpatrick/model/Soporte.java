package com.ecommerce.libreriasaintpatrick.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.util.List;

@Entity
@DiscriminatorValue("SOPORTE")
public class Soporte extends Producto {
    private String material;        // Ej: "madera", "metal"
    private Double peso;  // Peso aproximado en libras

    protected Soporte() {
        // Constructor requerido por JPA
    }

    public Soporte(Long id, String nombre, String descripcion, BigDecimal precio, Long stock,
                    String material, Double peso) {
        super(id, nombre, descripcion, precio, stock);
        this.material = material;
        this.peso = peso;
    }

    public String getMaterial() {
        return material;
    }

    public Double getPeso() {
        return peso;
    }

    public void setMaterial(String material) {
        if (material == null) throw new IllegalArgumentException("El material no puede ser nulo");

        String limpio = material.trim().toLowerCase();
        if (!List.of("yeso", "madera", "metal").contains(limpio)) {
            throw new IllegalArgumentException("Material inválido: debe ser 'yeso', 'madera' o 'metal'");
        }

        this.material = limpio;
    }

    public void setPeso(Double peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor a cero");
        }
        this.peso = peso;
    }

    @Override
    public String mostrarDetalle() {
        return String.format(
                "Soporte: %s [%d]\nMaterial: %s\nPeso: %.1f lbs.\nPrecio: $%.2f\nStock: %d unidades",
                getNombre(), getId(), material, peso,
                getPrecio().doubleValue(), getStock()
        );
    }
}

