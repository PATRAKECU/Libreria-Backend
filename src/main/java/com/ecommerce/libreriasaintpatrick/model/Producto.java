package com.ecommerce.libreriasaintpatrick.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo")
abstract public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Long stock;
    private Boolean demo = false; // ✅ campo demo

    public Boolean getDemo() {
        return demo;
    }

    public void setDemo(Boolean demo) {
        this.demo = demo;
    }


    protected Producto() {
        // Constructor requerido por JPA
    }

    public Producto(Long id, String nombre, String descripcion,
                    BigDecimal precio, Long stock) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        setPrecio(precio); // validación incluida
        setStock(stock);   // validación incluida
    }


    //Métodos consultores
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public BigDecimal getPrecio() { return precio; }
    public Long getStock() { return stock; }

    //Métodos modificadores
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setPrecio(BigDecimal precio) {
        if (precio == null || precio.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero");
        }
        this.precio = precio;
    }
    public void setStock(Long stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        this.stock = stock;
    }

    /** Reduce el stock en 1 unidad, lanza IllegalStateException si no hay stock */
    public void disminuirStock() {
        if (stock <= 0) throw new IllegalStateException("Sin stock disponible");
        stock--;
    }

    /** Método sobrecargado que reduce el stock en la cantidad de unidades requerida, lanza IllegalStateException si no hay stock */
    public void disminuirStock(Long cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad debe ser positiva");
        if (stock < cantidad) throw new IllegalStateException("No hay suficiente stock disponible");
        stock -= cantidad;
    }

    /** Incrementa el stock en la cantidad requerida */
    public void incrementarStock(Long cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad debe ser positiva");
        stock += cantidad;
    }

    abstract public String mostrarDetalle();
}

