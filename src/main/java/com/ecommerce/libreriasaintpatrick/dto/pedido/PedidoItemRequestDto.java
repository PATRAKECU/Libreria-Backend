package com.ecommerce.libreriasaintpatrick.dto.pedido;

import lombok.Data;

@Data
public class PedidoItemRequestDto implements ItemConProducto {
    private Long productoId;
    private Integer cantidad;
}

