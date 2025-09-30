package com.ecommerce.libreriasaintpatrick.dto.pedido;

import com.ecommerce.libreriasaintpatrick.dto.producto.ProductoDto;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PedidoItemDto {
    private ProductoDto producto;
    private BigDecimal precioUnitario;
    private Integer cantidad;
    private BigDecimal subtotal;
}
