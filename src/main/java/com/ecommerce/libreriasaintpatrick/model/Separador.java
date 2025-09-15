package com.ecommerce.libreriasaintpatrick.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.util.List;

@Entity
@DiscriminatorValue("SEPARADOR")
public class Separador extends Producto {
    private String material;    // Ej: "cartón", "plástico"
    private Double dimensionesCm;    //Tamaño en cm

    protected Separador() {
        // Constructor requerido por JPA
    }

    public Separador(Long id, String nombre, String descripcion, BigDecimal precio, Long stock,
                       String material, Double dimensionesCm) {
        super(id, nombre, descripcion, precio, stock);
        this.material = material;
        this.dimensionesCm = dimensionesCm;
    }

    public String getMaterial() {
        return material;
    }

    public Double getDimensionesCm() {
        return dimensionesCm;
    }

    public void setMaterial(String material) {
        if (material == null) throw new IllegalArgumentException("El material no puede ser nulo");

        String limpio = material.trim().toLowerCase();
        if (!List.of("tela", "madera", "metal", "cartón").contains(limpio)) {
            throw new IllegalArgumentException("Material inválido: debe ser 'tela', 'madera', 'metal' o 'cartón'");
        }

        this.material = limpio;
    }

    public void setDimensionesCm(Double dimensionesCm) {
        if (dimensionesCm <= 0) {
            throw new IllegalArgumentException("Las dimensiones deben ser mayores a cero");
        }
        this.dimensionesCm = dimensionesCm;
    }

    @Override
    public String mostrarDetalle() {
        return String.format(
                "Separador: %s [%d]\nMaterial: %s\nDimensiones: %.1f cm\nPrecio: $%.2f\nStock: %d unidades",
                getNombre(), getId(), material, dimensionesCm,
                getPrecio().doubleValue(), getStock()
        );
    }
}

