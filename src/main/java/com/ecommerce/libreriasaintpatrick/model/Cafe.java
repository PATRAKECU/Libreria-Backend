package com.ecommerce.libreriasaintpatrick.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
@DiscriminatorValue("CAFE")
public class Cafe extends Producto {
    private String origen;      // Ej: "Colombia", "Brasil"
    private String presentacion;  // Ej: "soluble", "pasado"
    private Double pesoGramos;  // Peso en gramos

    protected Cafe() {
        // Constructor requerido por JPA
    }

    public Cafe(Long id, String nombre, String descripcion, BigDecimal precio, Long stock,
                String origen, String presentacion, Double pesoGramos) {
        super(id, nombre, descripcion, precio, stock);
        this.origen = origen;
        this.presentacion = presentacion;
        this.pesoGramos = pesoGramos;
    }

    public String getOrigen() {
        return origen;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public double getPesoGramos() {
        return pesoGramos;
    }

    public void setOrigen(String origen) {
        if (origen == null || origen.trim().isEmpty()) {
            throw new IllegalArgumentException("El origen no puede estar vacío");
        }
        this.origen = origen.trim();
    }

    public void setPresentacion(String presentacion) {
        if (presentacion == null) throw new IllegalArgumentException("La presentación no puede ser nula");

        String limpio = presentacion.trim().toLowerCase();
        if (!limpio.equals("soluble") && !limpio.equals("molido") && !limpio.equals("en grano")) {
            throw new IllegalArgumentException("La presentación debe ser 'soluble', 'molido' o 'en grano'");
        }

        this.presentacion = limpio;
    }

    public void setPesoGramos(Double pesoGramos) {
        if (pesoGramos <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor a cero");
        }
        this.pesoGramos = pesoGramos;
    }

    @Override
    public String mostrarDetalle() {
        return String.format(
                "Café: %s [%d]\nOrigen: %s\nPresentación: %s\nPeso: %.1f g\nPrecio: $%.2f\nStock: %d unidades",
                getNombre(), getId(), origen, presentacion, pesoGramos,
                getPrecio().doubleValue(), getStock()
        );
    }
}
